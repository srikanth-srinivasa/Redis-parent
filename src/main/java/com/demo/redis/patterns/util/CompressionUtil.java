package com.demo.redis.patterns.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Class is to Compress the Payload.
 */
@Slf4j
@Component
public class CompressionUtil {

    /**
     * Private Constructor
     */
    public CompressionUtil() {
    }

    /**
     * Method will Encode the Compress Message
     *
     * @param text
     *
     * @return
     *
     * @throws IOException
     */
    public static String compressAndReturnB64(String text) throws IOException {
        try {
            return new String(Base64.getEncoder().encode(compress(text)));
        } catch (Exception e) {
            log.error("Exception Occured during Compression");
            throw e;
        }
    }

    /**
     * Method will call compress Method to compress Data.
     *
     * @param text
     *
     * @return
     *
     * @throws IOException
     */
    public static byte[] compress(String text) throws IOException {
        try {
            if (!text.isEmpty())
                return compress(text.getBytes());
        } catch (Exception e) {
            log.error("Payload can't be null or Empty");
            throw e;
        }
        return text.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Method will Compress and Return the Byte Array.
     *
     * @param bArray
     *
     * @return
     *
     * @throws IOException
     */
    public static byte[] compress(byte[] bArray) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (DeflaterOutputStream dos = new DeflaterOutputStream(os)) {
            dos.write(bArray);
        } catch (Exception e) {
            log.error("Exception Occured while Compressing and Encoding String Payload");
            throw e;
        }
        return os.toByteArray();
    }

    // this method will be used for compressing data to gzip
    public static byte[] gzipCompress(Object uncompressedData) throws IOException {
        byte[] result = null;
        try {
            // Establish byte array output stream
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            // Establish gzip compressed output stream
            GZIPOutputStream gzout = new GZIPOutputStream(o);
            // Establish object serialization output stream
            ObjectOutputStream out = new ObjectOutputStream(gzout);
            out.writeObject(uncompressedData);
            out.flush();
            out.close();
            gzout.close();
            // return compressed byte stream
            result = o.toByteArray();
            o.close();
        } catch (IOException e) {
            log.error("Error occured while decompressing to gzip", e);
            throw e;
        }
        return result;
    }

    // this method will be used for decompressing gzip data

}

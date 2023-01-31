package com.demo.redis.patterns.controller;


import com.demo.redis.patterns.dto.BookingAvro;
import com.demo.redis.patterns.service.CallingKafkaServiceImpl;
import com.demo.redis.patterns.service.impl.ProducerServiceImpl;

import net.apmoller.crb.telikos.libraries.kafkaconsumer.dto.BookingDTO;
//import net.apmoller.crb.telikos.libraries.kafkaconsumer.service.ReactiveConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This class  is  used as controller to  accept request for cache Read and cache write flow,
 */

@RestController
@RequestMapping("product")
public class ProductController {
    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProducerServiceImpl producerServiceImpl;

    @Autowired
    private CallingKafkaServiceImpl kafkaService;




    @PostMapping("/reactive-publish")
    public void publishMessageToTopic(@RequestBody BookingDTO booking){
        log.info("in reactive controller...publishMessageToTopic..");

        this.producerServiceImpl.send(booking.getBookingNumber(), booking);
    }

    @GetMapping("/reactive-subscribe")
    public Flux<BookingDTO>  consumeMessagesFromToTopic(){
        log.info("in reactive controller... consumeMessagesFromToTopic..");

        return kafkaService.consume();

    }


    @PostMapping("/reactive-publish-avro")
    public void publishMessageToAvroTopic(@RequestBody BookingAvro  bookingAvro){
        log.info("in reactive controller...publishMessageToAvroTopic..");

        this.producerServiceImpl.sendToAvroTopic(bookingAvro.getBookingNumber(), bookingAvro);
    }


    @GetMapping("/reactive-subscribe-avro")
    public Flux<BookingAvro>  consumeMessagesFromAvroTopic(){
        log.info("in reactive controller... consumeMessagesFromAvroTopic..");

        return kafkaService.consumeFromAvro();

    }




//    @PostMapping("/ohm-producer-library")
//    public void publishohmProducerLibraryToTopic(@RequestBody BookingDTO booking){
//        log.info("in reactive controller...publishohmProducerLibraryToTopic..");
//
//        ConcurrentHashMap topicMap = new ConcurrentHashMap();
//        topicMap.put("key","notification-topic");
//        topicMap.put("key","retry-topic");
//        topicMap.put("key","dead-letter-topic");
//
//
//        ConcurrentHashMap headerMap = new ConcurrentHashMap();
//        headerMap.put("X-Correlation-ID","123345");
//
//
//        this.ohmKafkaProducerService.produceMessages(topicMap,booking,headerMap);
//    }


}

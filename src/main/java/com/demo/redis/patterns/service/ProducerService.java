package com.demo.redis.patterns.service;

import com.demo.redis.patterns.dto.BookingAvro;
import net.apmoller.crb.telikos.libraries.kafkaconsumer.dto.BookingDTO;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface ProducerService {




    void send(String bookingNumber, BookingDTO booking);

    void sendToAvroTopic(String bookingNumber, BookingAvro bookingAvro);




}

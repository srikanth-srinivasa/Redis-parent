package com.demo.redis.patterns.service;

import net.apmoller.crb.telikos.libraries.kafkaconsumer.dto.BookingDTO;
import reactor.core.publisher.Mono;

public interface ProducerService {




    void send(String bookingNumber, BookingDTO booking);




}

package com.demo.redis.patterns.service;

import net.apmoller.crb.telikos.libraries.kafkaconsumer.dto.BookingDTO;
import net.apmoller.crb.telikos.microservices.cache.library.dto.ProductDto;
import net.apmoller.crb.telikos.microservices.cache.library.entity.Booking;
import reactor.core.publisher.Mono;

public interface ProductService {




    Mono<Booking> cacheAsideRead(String id);

    Mono<Void> cacheAsideWrite(ProductDto productDto);




}

package com.demo.redis.patterns.service;

import net.apmoller.crb.telikos.microservices.cache.library.dto.ProductDto;
import net.apmoller.crb.telikos.microservices.cache.library.entity.Booking;
import reactor.core.publisher.Mono;

public interface ProductService {



    Mono<Void> saveWriteThrough(ProductDto productDto);

    Mono<Void> saveWriteBehind(ProductDto productDto);

    Mono<Booking> readthrough(String id);

    Mono<Booking> cacheAside(String id);

    Mono<Booking> getProduct(String id);


}

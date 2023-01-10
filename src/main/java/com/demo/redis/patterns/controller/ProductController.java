package com.demo.redis.patterns.controller;


import com.demo.redis.patterns.service.ProductService;
import net.apmoller.crb.telikos.microservices.cache.library.dto.ProductDto;
import net.apmoller.crb.telikos.microservices.cache.library.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * This class  is  used as controller to  accept request for cache Read and cache write flow,
 */

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Method to  read  the cached valued for teh given key
     * @PathVariable id
     * @return Mono<Booking>
     */
    @GetMapping("/cacheAsideRead/{id}")
    public Mono<Booking> cacheAsideRead(@PathVariable String id) {

        return productService.cacheAsideRead(id);
    }

    /**
     * Method to  write to Redis cache
     * @PathVariable ProductDto
     * @return Mono<Void>
     */
    @PostMapping("/cacheAsideWrite")
    public Mono<Void> cacheAsideWrite(@RequestBody ProductDto productDto) {
        return productService.cacheAsideWrite(productDto);
    }




}

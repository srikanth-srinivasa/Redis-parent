package com.demo.redis.patterns.controller;


import com.demo.redis.patterns.service.ProductService;
import net.apmoller.crb.telikos.microservices.cache.library.dto.ProductDto;
import net.apmoller.crb.telikos.microservices.cache.library.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/saveWriteThrough")
    public Mono<Void> saveWriteThrough(@RequestBody ProductDto productDto) {
        return productService.saveWriteThrough(productDto);
    }


    @PostMapping("/saveWriteBehind")
    public Mono<Void> saveWriteBehind(@RequestBody  ProductDto productDto) {
        return productService.saveWriteBehind(productDto);
    }


    @GetMapping("readthrough/{id}")
    public Mono<Booking> readthrough(@PathVariable String id){
        return this.productService.readthrough(id);
    }

    @GetMapping("/cacheAsideRead/{id}")
    public Mono<Booking> cacheAsideRead(@PathVariable String id) {
        return productService.cacheAsideRead(id);
    }

    @PostMapping("/cacheAsideWrite")
    public Mono<Void> cacheAsideWrite(@RequestBody ProductDto productDto) {
        return productService.cacheAsideWrite(productDto);
    }

    @GetMapping("{id}")
    public Mono<Booking> getProduct(@PathVariable String id){
        return this.productService.getProduct(id);
    }


}

package com.demo.redis.patterns.service.impl;


import net.apmoller.crb.telikos.microservices.cache.library.annotation.*;
import net.apmoller.crb.telikos.microservices.cache.library.dto.ProductDto;
import com.demo.redis.patterns.service.ProductService;
import net.apmoller.crb.telikos.microservices.cache.library.entity.Booking;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

  @Override
  @WriteThroughCache(key =  "#productDto.id")
  public Mono<Void> saveWriteThrough(ProductDto productDto) {
    return Mono.empty().then();

  }

  @Override
  @WriteBehindCache(key =  "#productDto.id")
  public Mono<Void> saveWriteBehind(ProductDto productDto) {
    return Mono.empty().then();
  }

  @Override
  @ReadThroughCache(key = "#id")
  public Mono<Booking> readthrough(String id) {
    return this.readthrough(id);
  }


  @Override
  @CacheAsideRead(key = "#id")
  public Mono<Booking> cacheAsideRead(String id) {
    return this.cacheAsideRead(id);
  }


  @Override
  @CacheAsideWrite(key =  "#productDto.id")
  public Mono<Void> cacheAsideWrite(ProductDto productDto) {
    return Mono.empty().then();

  }

  @Override
  public Mono<Booking> getProduct(String id) {
        return this.getProduct(id);

  }

}

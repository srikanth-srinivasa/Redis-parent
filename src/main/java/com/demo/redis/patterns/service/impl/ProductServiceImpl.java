package com.demo.redis.patterns.service.impl;




import net.apmoller.crb.telikos.libraries.redis.annotation.CacheAsideRead;
import net.apmoller.crb.telikos.libraries.redis.annotation.CacheAsideWrite;

import com.demo.redis.patterns.service.ProductService;

import net.apmoller.crb.telikos.microservices.cache.library.dto.ProductDto;
import net.apmoller.crb.telikos.microservices.cache.library.entity.Booking;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {


  /**
   * Method to  read  the cached valued for teh given key
   * @PathVariable id
   * @return Mono<Booking>
   */
  @Override
  @CacheAsideRead(key = "#id")
  public Mono<Booking> cacheAsideRead(String id) {
    return this.cacheAsideRead(id);
  }


  /**
   * Method to  write to Redis cache
   * @PathVariable ProductDto
   * @return Mono<Void>
   */
  @Override
  @CacheAsideWrite(key =  "#productDto.id")
  public Mono<Void> cacheAsideWrite(ProductDto productDto) {
    return Mono.empty().then();

  }



}

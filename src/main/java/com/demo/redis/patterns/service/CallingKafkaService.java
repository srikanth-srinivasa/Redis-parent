package com.demo.redis.patterns.service;

import reactor.core.publisher.Flux;

/**
 *
 * @param <K> k is the  type of key of the Object to be cached
 * @param <V> V is the type of Value
 */
public interface CallingKafkaService<K, V> {




    Flux<V> consume();
}

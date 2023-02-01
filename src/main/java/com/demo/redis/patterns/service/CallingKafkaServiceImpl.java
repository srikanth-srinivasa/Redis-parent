package com.demo.redis.patterns.service;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@Slf4j
@Setter
public class CallingKafkaServiceImpl<K,V> implements CallingKafkaService<K,V> {


    @Autowired
    ReactiveKafkaConsumerTemplate<K,V> ReactiveKafkaAvroConsumerTemplate;


    @Autowired
    ReactiveKafkaConsumerTemplate<K,V> ReactiveKafkaConsumerPlainTemplate;

    public CallingKafkaServiceImpl(ReactiveKafkaConsumerTemplate<K, V> ReactiveKafkaAvroConsumerTemplate, ReactiveKafkaConsumerTemplate<K, V> ReactiveKafkaConsumerPlainTemplate) {
        ReactiveKafkaAvroConsumerTemplate = ReactiveKafkaAvroConsumerTemplate;
        ReactiveKafkaConsumerPlainTemplate = ReactiveKafkaConsumerPlainTemplate;
    }


    @Override
    public Flux<V> consume() {

        return (Flux<V>) ReactiveKafkaConsumerPlainTemplate
            .receiveAutoAck()
            // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
            .doOnNext(consumerRecord -> log.info("received key={}, value={} : " +
                    " from topic={}, offset={}" ,
                    consumerRecord.key(),
                    consumerRecord.value(),
                    consumerRecord.topic(),
                       consumerRecord.offset())
            )
            .map(ConsumerRecord::value)
            .doOnNext(order -> log.info("successfully consumed {}",  order))
            .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));


    }

    @Override
    public Flux<V> consumeFromAvro() {

        return (Flux<V>) ReactiveKafkaAvroConsumerTemplate
                .receiveAutoAck()
                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                .doOnNext(consumerRecord -> log.info("received  from consumeFromAvro key={}, value={} : " +
                                " from topic={}, offset={}" ,
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(order -> log.info("successfully consumed from consumeFromAvro {}",  order))
                .doOnError(throwable -> log.error("something bad happened while consuming from consumeFromAvro : {}", throwable.getMessage()));
    }


}

package com.demo.redis.patterns.service.impl;


import com.demo.redis.patterns.service.ProducerService;
import net.apmoller.crb.telikos.libraries.kafkaconsumer.dto.BookingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl implements ProducerService {



  private final Logger log = LoggerFactory.getLogger(ProducerServiceImpl.class);


  @Autowired
  private final ReactiveKafkaProducerTemplate<String, BookingDTO> reactiveKafkaProducerTemplate;

  @Value("${kafka.topic}")
  private String topic;


  public ProducerServiceImpl(ReactiveKafkaProducerTemplate<String, BookingDTO> reactiveKafkaProducerTemplate) {
    this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
  }

  @Override
  public void send(String bookingNumber, BookingDTO booking) {

    log.info("In ReactiveProducerService---- sending  to topic={}, {}={},", topic, BookingDTO.class.getSimpleName(), booking);
    reactiveKafkaProducerTemplate.send(topic, bookingNumber, booking)
            .doOnSuccess(senderResult -> log.info("In ReactiveProducerService---- succesfully sent {}==>for  offset : {}", booking, senderResult.recordMetadata().offset()))
            .subscribe();

  }



}

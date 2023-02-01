package com.demo.redis.patterns.service.impl;


import com.demo.redis.patterns.dto.BookingAvro;
import com.demo.redis.patterns.service.ProducerService;
import net.apmoller.crb.telikos.libraries.kafkaconsumer.dto.BookingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProducerServiceImpl implements ProducerService {



  private final Logger log = LoggerFactory.getLogger(ProducerServiceImpl.class);


  @Autowired
  private final ReactiveKafkaProducerTemplate<String, BookingDTO> reactiveKafkaProducerTemplate;


  @Autowired
  private final ReactiveKafkaProducerTemplate<String, BookingAvro> reactiveKafkaProducerAvroTemplate;



  @Value("${kafka.plain-topic}")
  private String plaintopic;

  @Value("${kafka.avro-topic}")
  private String avrotopic;



  public ProducerServiceImpl(ReactiveKafkaProducerTemplate<String, BookingDTO> reactiveKafkaProducerTemplate, ReactiveKafkaProducerTemplate<String, BookingAvro> reactiveKafkaProducerAvroTemplate) {
    this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    this.reactiveKafkaProducerAvroTemplate = reactiveKafkaProducerAvroTemplate;
  }

  @Override
  public void send(String bookingNumber, BookingDTO booking) {

    log.info("In ReactiveProducerService---- sending  to demo_topic ={}, {}={},", plaintopic, BookingDTO.class.getSimpleName(), booking);
    reactiveKafkaProducerTemplate.send(plaintopic, bookingNumber, booking)
            .doOnSuccess(senderResult -> log.info("In ReactiveProducerService---- succesfully sent to demo_topic {}==>for  offset : {}", booking, senderResult.recordMetadata().offset()))
            .subscribe();

  }


  @Override
  public void sendToAvroTopic(String bookingNumber, BookingAvro bookingAvro) {



      log.info("In ReactiveProducerService---- publishMessageToAvroTopic--sending  to sendToAvroTopic ={}, {}={},", avrotopic, BookingAvro.class.getSimpleName(), bookingAvro);
      reactiveKafkaProducerAvroTemplate.send(avrotopic, bookingNumber,bookingAvro)
              .doOnSuccess(senderResult -> log.info("In ReactiveProducerService---- publishMessageToAvroTopic --- succesfully sent {}==>for  Topic : {}", bookingAvro,avrotopic))
              .doOnError(e -> log.error("Send failed-----", e))
              .subscribe();

  }



}

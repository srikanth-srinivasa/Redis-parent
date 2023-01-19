package net.apmoller.crb.telikos.libraries.kafkaconsumer.service;


import net.apmoller.crb.telikos.libraries.kafkaconsumer.dto.BookingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReactiveProducerService {

    private final Logger log = LoggerFactory.getLogger(ReactiveProducerService.class);

    private final ReactiveKafkaProducerTemplate<String, BookingDTO> reactiveKafkaProducerTemplate;

    @Value("${PRODUCER_DTO_TOPIC}")
    private String topic;

    public ReactiveProducerService(ReactiveKafkaProducerTemplate<String, BookingDTO> reactiveKafkaProducerTemplate) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    public void send(String bookingNumber, BookingDTO booking) {
        log.info("In ReactiveProducerService---- sending  to topic={}, {}={},", topic, BookingDTO.class.getSimpleName(), booking);
        reactiveKafkaProducerTemplate.send(topic, bookingNumber, booking)
                .doOnSuccess(senderResult -> log.info("In ReactiveProducerService---- succesfully sent {}==>for  offset : {}", booking, senderResult.recordMetadata().offset()))
                .subscribe();

    }

}
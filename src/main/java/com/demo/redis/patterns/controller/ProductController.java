package com.demo.redis.patterns.controller;


import com.demo.redis.patterns.service.CallingKafkaServiceImpl;
import com.demo.redis.patterns.service.impl.ProducerServiceImpl;
import net.apmoller.crb.telikos.libraries.kafkaconsumer.dto.BookingDTO;
//import net.apmoller.crb.telikos.libraries.kafkaconsumer.service.ReactiveConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class  is  used as controller to  accept request for cache Read and cache write flow,
 */

@RestController
@RequestMapping("product")
public class ProductController {
    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProducerServiceImpl producerServiceImpl;

    @Autowired
    private CallingKafkaServiceImpl kafkaService;

    @PostMapping("/reactive-publish")
    public void publishMessageToTopic(@RequestBody BookingDTO booking){
        log.info("in reactive controller...publishMessageToTopic..");

        this.producerServiceImpl.send(booking.getBookingNumber(), booking);
    }

    @GetMapping("/reactive-subscribe")
    public Flux<BookingDTO>  consumeMessagesFromToTopic(){
        log.info("in reactive controller... consumeMessagesFromToTopic..");

        return kafkaService.consume();

    }

}

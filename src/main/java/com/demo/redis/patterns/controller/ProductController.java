package com.demo.redis.patterns.controller;


import com.demo.redis.patterns.service.ProductService;
import net.apmoller.crb.telikos.libraries.kafkaconsumer.dto.BookingDTO;
import net.apmoller.crb.telikos.libraries.kafkaconsumer.service.ReactiveConsumerService;
import net.apmoller.crb.telikos.libraries.kafkaconsumer.service.ReactiveProducerService;
import net.apmoller.crb.telikos.microservices.cache.library.dto.ProductDto;
import net.apmoller.crb.telikos.microservices.cache.library.entity.Booking;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * This class  is  used as controller to  accept request for cache Read and cache write flow,
 */

@RestController
@RequestMapping("product")
public class ProductController {
    private final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ReactiveProducerService producer;

    @Autowired
    private ReactiveConsumerService reactiveConsumerService;


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


    @PostMapping("/reactive-publish")
    public void publishMessageToTopic(@RequestBody BookingDTO booking){
        log.info("in reactive controller...publishMessageToTopic..");

        this.producer.send(booking.getBookingNumber(), booking);
    }

    @GetMapping("/reactive-subscribe")
    public Flux<BookingDTO>  consumeMessagesFromToTopic(){
        log.info("in reactive controller... consumeMessagesFromToTopic..");

        return reactiveConsumerService.consumeMessages().doOnNext(
                order -> log.info("In ReactiveConsumerService----successfully consumed {}==>{}", BookingDTO.class.getSimpleName(), order.getBookingNumber(), order.getExternalBookingIdentifier()  ))
                .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));

    }

    @GetMapping("/reactive-subscribe1")
    public List<BookingDTO>  consumeMessagesFromToTopic1(){
        log.info("in reactive controller... consumeMessagesFromToTopic1..");

        List<BookingDTO> bookingList = new ArrayList<>();

         reactiveConsumerService.consumeMessages()
                 .doOnNext(  order -> {
                     log.info("In ReactiveConsumerService----successfully consumed {}==>{}", BookingDTO.class.getSimpleName(), order.getBookingNumber(), order.getExternalBookingIdentifier()  );
                             Flux.range(1, 10)
                                     .map(i -> bookingList.add(new BookingDTO(order.getBookingNumber(),order.getExternalBookingIdentifier(),order.getTotalBookedPackageQuantity()))
                                     );

                                  }


                 );


        log.info("in reactive controller... consumeMessagesFromToTopic1..");

        bookingList.forEach(bookingDTO -> System.out.println("-------" +bookingDTO));

        return bookingList;

    }

}

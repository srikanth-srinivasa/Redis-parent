package net.apmoller.crb.telikos.microservices.cache.library.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ProductDto implements Serializable {

    private String id;
    private String description;
    private String price;


}

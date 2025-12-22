package com.tp.common.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long sellerId;
    private Long categoryId;
    private Long addressId;
    private String name;
    private String description;
    private String image;
    private Double price;
    private Integer quantity;

    private LocalDateTime creatTime;
    private LocalDateTime updateTime;
}

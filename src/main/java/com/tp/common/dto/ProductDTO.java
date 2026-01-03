package com.tp.common.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long sellerId;
    private Long categoryId;
    private Long addressId;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Integer status;
    private String image;
    private Integer condition;
}

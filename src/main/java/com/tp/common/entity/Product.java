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
    // 商品成色：0：全新, 1：99新, 2：95新, 3：9成新,4： 8成新以下
    private Integer condition;
    // 商品状态：0-下架，1-在售，2-已卖出，3-已预订
    private Integer status;

    private LocalDateTime creatTime;
    private LocalDateTime updateTime;
}

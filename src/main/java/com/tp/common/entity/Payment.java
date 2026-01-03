package com.tp.common.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Payment {
    private Long id;
    private Long buyerId;
    private Long productId;
    private Double amount;
    private String status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.tp.common.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Collects {
    private Long id;
    private Long userId;
    private Long productId;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

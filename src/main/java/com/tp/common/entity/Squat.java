package com.tp.common.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Squat {
    private Long id;
    private Long userId;
    private Long productId;
    // 提醒方式: 0-降价提醒，1-上架提醒
    private Integer remindType;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.tp.common.dto;

import lombok.Data;

@Data
public class SquatDTO {
    private Long userId;
    private Long productId;
    // 提醒方式: 0-降价提醒，1-上架提醒
    private Integer remindType;
}

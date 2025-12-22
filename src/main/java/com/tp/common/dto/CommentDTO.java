package com.tp.common.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long userId;
    private Long orderId;
    private Long productId;
    private String content;
    private Integer star;
}

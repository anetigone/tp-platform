package com.tp.common.dto;

import lombok.Data;

@Data
public class SendMessageDTO {
    private Long productId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private Integer messageType; // 1-文本，2-图片，3-语音
}

package com.tp.common.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageVO {
    private Long id;
    private String content;
    private Integer messageType;// 1-文本，2-图片，3-语音
    private Integer isRead;// 0-未读，1-已读
    private LocalDateTime createdTime;
    private Boolean isMyMessage; // 是否是自己发送的消息
}
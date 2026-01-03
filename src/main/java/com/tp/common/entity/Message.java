package com.tp.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Long id;
    private Long conversationId;
    private Long senderId;
    private Long receiverId;
    private String content;
    private Integer messageType; // 1-文本，2-图片，3-语音
    private Integer isRead; // 0-未读，1-已读

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 关联属性
    private User sender; // 发送方用户信息
}
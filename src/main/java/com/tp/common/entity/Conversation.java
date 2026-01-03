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
public class Conversation {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private Long lastMessageId;
    private Long senderLastMessageId;
    private Long receiverLastMessageId;
    private Integer senderUnreadCount;
    private Integer receiverUnreadCount;
    private Integer senderDeleted;// 0-未删除，1-已删除
    private Integer receiverDeleted;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 关联属性
    private User otherUser; // 对方用户信息
    private Message lastMessage; // 最后一条消息
    private Integer unreadCount; // 当前用户的未读消息数
}
package com.tp.common.vo;

import com.tp.common.entity.Message;
import com.tp.common.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ConversationVO {
    private Long id;
    private User otherUser; // 对方用户信息
    private Message lastMessage; // 最后一条消息
    private Integer unreadCount; // 未读消息数

    private LocalDateTime updatedTime;
}
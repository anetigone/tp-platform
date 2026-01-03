package com.tp.common.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.tp.common.entity.Message}
 */
@Data
public class MessageDto implements Serializable {
    Long conversationId;
    Long senderId;
    Long receiverId;
    String content;
    Long productId;
    Integer status;
}
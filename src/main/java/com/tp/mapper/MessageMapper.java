package com.tp.mapper;

import com.tp.common.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    List<Message> getByConversationId(Long conversationId);

    Message getById(Long id);

    int insert(Message message);

    int batchMarkAsRead(List<Long> messageIds);

    List<Message> listByConversationId(Long conversationId, Integer offset, Integer limit);

    Long getMessageCount(Long conversationId);
}

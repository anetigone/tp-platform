package com.tp.mapper;

import com.tp.common.entity.Conversation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConversationMapper {

    List<Conversation> getByUserId(Long userId);

    Conversation getById(Long id);

    Conversation getBySenderAndReceiver(Long senderId, Long receiverId);

    int insert(Conversation conversation);

    int update(Conversation conversation);

    int deleteById(Long id);
}

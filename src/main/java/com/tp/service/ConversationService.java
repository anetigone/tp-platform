package com.tp.service;

import com.tp.common.entity.Conversation;
import com.tp.common.vo.ConversationVO;

import java.util.List;

public interface ConversationService {

    List<ConversationVO> getByUserId(Long userId);

    Conversation getOrCreate(Long senderId, Long receiverId);

    boolean delete(Long id, Long userId);
}

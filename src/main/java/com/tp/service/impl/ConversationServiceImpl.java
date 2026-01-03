package com.tp.service.impl;

import com.tp.common.entity.Conversation;
import com.tp.common.entity.Message;
import com.tp.common.entity.User;
import com.tp.common.exception.ConversationException;
import com.tp.common.exception.ExceptionMessage;
import com.tp.common.vo.ConversationVO;
import com.tp.mapper.ConversationMapper;
import com.tp.mapper.MessageMapper;
import com.tp.mapper.UserMapper;
import com.tp.service.ConversationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationMapper conversationMapper;
    private final UserMapper userMapper;
    private final MessageMapper messageMapper;

    public ConversationServiceImpl(ConversationMapper conversationMapper,
                                   UserMapper userMapper,
                                   MessageMapper messageMapper) {
        this.conversationMapper = conversationMapper;
        this.userMapper = userMapper;
        this.messageMapper = messageMapper;
    }

    @Override
    public List<ConversationVO> getByUserId(Long userId) {
        if (userId == null) {
            throw new ConversationException(ExceptionMessage.CONVERSATION_USER_ID_NULL);
        }
        List<Conversation> list = conversationMapper.getByUserId(userId);

        return list.stream()
                .filter(conv -> !isConversationDeleted(conv, userId))
                .map(conv -> buildConversationVO(conv, userId))
                .sorted((a, b) -> b.getUpdatedTime().compareTo(a.getUpdatedTime()))
                .toList();
    }

    @Override
    @Transactional
    public Conversation getOrCreate(Long senderId, Long receiverId) {
        Conversation conversation = conversationMapper.getBySenderAndReceiver(senderId, receiverId);
        if (conversation == null) {
            conversation = new Conversation();
            conversation.setSenderId(senderId);
            conversation.setReceiverId(receiverId);
            conversation.setSenderUnreadCount(0);
            conversation.setReceiverUnreadCount(0);
            conversation.setSenderDeleted(0);
            conversation.setReceiverDeleted(0);

            conversationMapper.insert(conversation);
        }

        return conversation;
    }

    @Override
    public boolean delete(Long id, Long userId) {
        Conversation conv = conversationMapper.getById(id);
        if (conv == null ||
            (!conv.getSenderId().equals(userId) && !conv.getReceiverId().equals(userId))) {
            throw new ConversationException(ExceptionMessage.NO_PERMISSION);
        }
        if (conv.getSenderId().equals(userId)) {
            conv.setSenderDeleted(1);
        }
        else {
            conv.setReceiverDeleted(1);
        }
        return conversationMapper.update(conv) > 0;
    }

    private ConversationVO buildConversationVO(Conversation conv, Long userId) {
        Long otherUserId = conv.getSenderId().equals(userId) ? conv.getReceiverId() : conv.getSenderId();
        User otherUser = userMapper.getById(otherUserId);

        Long lastMessageId = getLastMessageId(conv, userId);
        Message lastMessage = messageMapper.getById(lastMessageId);

        Integer unreadCount = getUnreadCount(conv, userId);

        return ConversationVO.builder()
                .id(conv.getId())
                .otherUser(otherUser)
                .lastMessage(lastMessage)
                .unreadCount(unreadCount)
                .updatedTime(conv.getUpdateTime())
                .build();
    }

    private Long getLastMessageId(Conversation conv, Long userId) {
        return conv.getSenderId().equals(userId) ? conv.getSenderLastMessageId() : conv.getReceiverLastMessageId();
    }

    private Integer getUnreadCount(Conversation conv, Long userId) {
        return conv.getSenderId().equals(userId) ? conv.getSenderUnreadCount() : conv.getReceiverUnreadCount();
    }

    private boolean isConversationDeleted(Conversation conv, Long userId) {
        if(conv.getSenderId().equals(userId)) {
            return conv.getSenderDeleted() == 1;
        }
        else {
            return conv.getReceiverDeleted() == 1;
        }
    }
}

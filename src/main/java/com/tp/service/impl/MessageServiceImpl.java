package com.tp.service.impl;

import com.tp.common.dto.SendMessageDTO;
import com.tp.common.entity.Conversation;
import com.tp.common.entity.Message;
import com.tp.common.exception.ExceptionMessage;
import com.tp.common.exception.MessageException;
import com.tp.common.vo.MessageVO;
import com.tp.mapper.ConversationMapper;
import com.tp.mapper.MessageMapper;
import com.tp.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;
    private final ConversationMapper conversationMapper;

    public MessageServiceImpl(MessageMapper messageMapper, ConversationMapper conversationMapper) {
        this.messageMapper = messageMapper;
        this.conversationMapper = conversationMapper;
    }

    @Override
    @Transactional
    public Message sendMessage(SendMessageDTO dto) {
        Long senderId = dto.getSenderId();
        Long receiverId = dto.getReceiverId();
        Conversation conversation = conversationMapper.getBySenderAndReceiver(senderId, receiverId);
        if(conversation == null) {
            conversation = new Conversation();
            conversation.setSenderId(senderId);
            conversation.setReceiverId(receiverId);
            conversation.setSenderUnreadCount(0);
            conversation.setReceiverUnreadCount(0);
            conversation.setSenderDeleted(0);
            conversation.setReceiverDeleted(0);

            conversationMapper.insert(conversation);
        }

        Message message = Message.builder()
                .conversationId(conversation.getId())
                .senderId(senderId)
                .receiverId(receiverId)
                .content(dto.getContent())
                .messageType(dto.getMessageType())
                .isRead(0)
                .build();

        messageMapper.insert(message);

        updateConversation(conversation, message);

        return message;
    }

    @Override
    public List<MessageVO> getMessageList(Long conversationId, Long userId, Integer page, Integer size) {
        Conversation conversation = conversationMapper.getById(conversationId);
        if (conversation == null ||
            (!conversation.getSenderId().equals(userId) && !conversation.getReceiverId().equals(userId))) {
            throw new MessageException(ExceptionMessage.NO_PERMISSION);
        }

        Integer offset = (page - 1) * size;
        Integer limit = size;

        List<Message> list = messageMapper.listByConversationId(conversationId, offset, limit);
        List<Long> messageIds = list.stream()
                .map(Message::getId)
                .toList();

        markAsRead(conversation, userId, messageIds);

        return list.stream()
                .map(message -> buildMessageVO(message, userId))
                .toList();
    }

    @Override
    public Long getMessageCount(Long conversationId, Long userId) {
        return messageMapper.getMessageCount(conversationId);
    }

    @Override
    public String uploadImage(MultipartFile file) {
        //todo 上传图片
        return "";
    }

    private void updateConversation(Conversation conv, Message message) {
        conv.setLastMessageId(message.getId());
        if(message.getSenderId().equals(conv.getSenderId())) {
            conv.setSenderLastMessageId(message.getId());
        }
        else {
            conv.setReceiverLastMessageId(message.getId());
        }

        if(message.getSenderId().equals(conv.getSenderId())) {
            conv.setSenderUnreadCount(conv.getSenderUnreadCount() + 1);
        }
        else {
            conv.setReceiverUnreadCount(conv.getReceiverUnreadCount() + 1);
        }

        conversationMapper.update(conv);
    }

    private void markAsRead(Conversation conv, Long userId, List<Long> messageIds) {
        if(!messageIds.isEmpty()) {
            messageMapper.batchMarkAsRead(messageIds);
            if(conv.getSenderId().equals(userId)) {
                conv.setSenderUnreadCount(0);
            }
            else {
                conv.setReceiverUnreadCount(0);
            }
            conversationMapper.update(conv);
        }
    }

    private MessageVO buildMessageVO(Message message, Long userId) {
        return MessageVO.builder()
                .id(message.getId())
                .content(message.getContent())
                .messageType(message.getMessageType())
                .isRead(message.getIsRead())
                .createdTime(message.getCreateTime())
                .isMyMessage(message.getSenderId().equals(userId))
                .build();
    }
}

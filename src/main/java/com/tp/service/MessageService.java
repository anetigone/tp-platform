package com.tp.service;

import com.tp.common.dto.SendMessageDTO;
import com.tp.common.entity.Message;
import com.tp.common.vo.MessageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {

    Message sendMessage(SendMessageDTO dto);

    List<MessageVO> getMessageList(Long conversationId, Long userId, Integer page, Integer size);

    Long getMessageCount(Long conversationId, Long userId);

    String uploadImage(MultipartFile file);
}

package com.tp.controller;

import com.tp.common.dto.SendMessageDTO;
import com.tp.common.entity.Message;
import com.tp.common.result.PageResult;
import com.tp.common.result.Result;
import com.tp.common.vo.MessageVO;
import com.tp.service.MessageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 发送消息
     */
    @PostMapping("")
    public Result<Message> sendMessage(@RequestBody SendMessageDTO dto) {
        Message message = messageService.sendMessage(dto);
        return Result.success(message);
    }

    /**
     * 获取会话的消息列表
     */
    @GetMapping("/conversation/{conversationId}")
    public PageResult<MessageVO> getMessageList(
            @PathVariable Long conversationId,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {

        List<MessageVO> messages = messageService.getMessageList(conversationId, userId, page, size);
        Long total = messageService.getMessageCount(conversationId, userId);
        return PageResult.success(messages, total, page, size);
    }

    /**
     * 上传图片消息
     */
    @PostMapping("/image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String imageUrl = messageService.uploadImage(file);
        return Result.success(imageUrl);
    }
}

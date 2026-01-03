package com.tp.controller;

import com.tp.common.result.Result;
import com.tp.common.vo.ConversationVO;
import com.tp.service.ConversationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conversation")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @GetMapping("/list")
    public Result<List<ConversationVO>> list(@RequestParam Long userId) {
        List<ConversationVO> list = conversationService.getByUserId(userId);
        return Result.success(list);
    }

    @PostMapping("/delete/{id}")
    public Result<Void> delete(@PathVariable Long id, @RequestParam Long userId) {
        conversationService.delete(id, userId);
        return Result.success();
    }
}

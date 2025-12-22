package com.tp.controller.admin;

import com.tp.common.entity.User;
import com.tp.common.result.PageResult;
import com.tp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public PageResult<User> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("获取用户列表：page={}, size={}", page, size);
        List<User> users = userService.getPage(page, size);
        Long total = userService.getTotalCount();
        return PageResult.success(users, total, page, size);
    }
}

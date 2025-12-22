package com.tp.controller.admin;

import com.tp.common.context.UserContext;
import com.tp.common.context.UserType;
import com.tp.common.dto.LoginDTO;
import com.tp.common.entity.Admin;
import com.tp.common.result.Result;
import com.tp.common.vo.LoginVO;
import com.tp.service.AdminService;
import com.tp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final JwtUtil jwtUtil;

    public AdminController(AdminService adminService, JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        log.info("管理员登录：{}", dto);

        Admin admin = adminService.login(dto.getUsername(), dto.getPassword());
        UserContext userContext = UserContext.builder()
                .userId(admin.getId())
                .username(admin.getUsername())
                .role(UserType.ADMIN)
                .build();
        String token = jwtUtil.generateToken(admin.getUsername());

        LoginVO loginVO = LoginVO.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .role(UserType.ADMIN)
                .token(token)
                .build();

        return Result.success(loginVO);
    }
}

package com.tp.controller.user;

import com.tp.common.context.BaseContext;
import com.tp.common.context.UserContext;
import com.tp.common.context.UserType;
import com.tp.common.dto.LoginDTO;
import com.tp.common.dto.UserDTO;
import com.tp.common.entity.User;
import com.tp.common.result.Result;
import com.tp.common.result.ResultMessage;
import com.tp.common.vo.LoginVO;
import com.tp.common.vo.UserInfoVO;
import com.tp.service.UserService;
import com.tp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 用户注册
     * @param userDTO 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserDTO userDTO) {
        log.info("用户注册：{}", userDTO);

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        boolean registered = userService.register(user);

        return Result.success(ResultMessage.REGISTER_SUCCESS);
    }

    /**
     * 用户登录
     * @param dto 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO dto) {
        log.info("用户登录：{}", dto);

        User loginUser = userService.login(dto.getUsername(), dto.getPassword());
        UserContext context = UserContext.builder()
                .userId(loginUser.getId())
                .username(loginUser.getUsername())
                .role(UserType.USER)
                .build();
        BaseContext.setUserContext(context);

        Map<String, Object> map = new HashMap<>();
        map.put("userId", loginUser.getId().toString());
        map.put("role", UserType.USER);

        String token = jwtUtil.generateToken(loginUser.getUsername(), map);
        LoginVO loginVO = LoginVO.builder()
                .id(loginUser.getId())
                .username(loginUser.getUsername())
                .role(0)
                .token(token)
                .build();

        return Result.success(loginVO);
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<User> info() {
        log.info("获取用户信息");
        Long id = BaseContext.getCurrentUserId();

        User user = userService.getById(id);

        return Result.success(user);
    }

    /**
     * 获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/info/{id}")
    public Result<UserInfoVO> info(@PathVariable("id") Long id) {
        log.info("获取用户信息：{}", id);
        User user = userService.getById(id);

        UserInfoVO info = UserInfoVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .build();

        return Result.success(info);
    }

    /**
     * 修改用户信息
     * @param userDTO 用户信息
     * @return 修改结果
     */
    @PostMapping("/update")
    public Result<String> update(@RequestBody UserDTO userDTO) {
        log.info("修改用户信息：{}", userDTO);
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userService.updateById(user);

        return Result.success(ResultMessage.UPDATE_SUCCESS);
    }

    /**
     * 退出登录
     * @return 退出登录结果
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        log.info("退出登录");
        BaseContext.clear();
        return Result.success(ResultMessage.LOGOUT_SUCCESS);
    }
}
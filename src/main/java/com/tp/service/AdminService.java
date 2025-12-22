package com.tp.service;

import com.tp.common.entity.Admin;
import org.springframework.stereotype.Service;

public interface AdminService {

    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的管理员信息
     */
    Admin login(String username, String password);
}

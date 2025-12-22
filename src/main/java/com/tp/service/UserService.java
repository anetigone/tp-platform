package com.tp.service;

import com.tp.common.entity.User;

import java.util.List;

public interface UserService {
    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册结果
     */
    boolean register(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户信息
     */
    User login(String username, String password);

    /**
     * 根据用户ID查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    User getById(Long id);

    /**
     * 根据用户ID更新用户信息
     * @param user 用户信息
     * @return 更新结果
     */
    int updateById(User user);

    /**
     * 分页查询用户信息
     * @param page 页码
     * @param size 每页大小
     * @return 用户列表
     */
    List<User> getPage(Integer page, Integer size);

    /**
     * 获取用户总数
     *
     * @return 用户总数
     */
    Long getTotalCount();
}
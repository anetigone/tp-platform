package com.tp.service.impl;

import com.tp.common.entity.User;
import com.tp.common.exception.*;
import com.tp.mapper.UserMapper;
import com.tp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        // 检查用户名是否已存在
        User existingUser = userMapper.getByUsername(user.getUsername());
        if (existingUser != null) {
            throw new UsernameDuplicateException(ExceptionMessage.USERNAME_DUPLICATE);
        }

        // 设置创建时间和更新时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        // 设置默认状态
        user.setStatus(1); // 正常状态
        
        // 插入新用户
        return userMapper.insert(user) > 0;
    }

    @Override
    public User login(String username, String password) {
        // 根据用户名查找用户
        User user = userMapper.getByUsername(username);
        
        // 检查用户是否存在且密码匹配且状态正常
        if(user == null) {
            throw new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND);
        }

        if(!user.getPassword().equals(password)) {
            throw new PasswordMismatchException(ExceptionMessage.PASSWORD_MISMATCH);
        }

        if(user.getStatus() != 1) {
            throw new UserBannedException(ExceptionMessage.USER_FORBIDDEN);
        }
        
        return user;
    }

    @Override
    public User getById(Long id) {
        User user = userMapper.getById(id);

        // 检查用户是否存在
        if(user == null) {
            throw new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND);
        }

        return user;
    }

    @Override
    public List<User> getPage(Integer page, Integer size) {
        int offset = (page - 1) * size;
        int limit = size;

        return userMapper.getPage(offset, limit);
    }

    @Override
    public Long getTotalCount() {
        return userMapper.count();
    }

    @Override
    public int updateById(User user) {
        return userMapper.updateById(user);
    }
}
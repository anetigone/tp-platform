package com.tp.service.impl;

import com.tp.common.entity.Admin;
import com.tp.common.exception.ExceptionMessage;
import com.tp.common.exception.PasswordMismatchException;
import com.tp.common.exception.UserNotFoundException;
import com.tp.mapper.AdminMapper;
import com.tp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String username, String password) {

        Admin admin = adminMapper.getByUsername(username);

        if(admin == null) {
            throw new UserNotFoundException(ExceptionMessage.USER_NOT_FOUND);
        }

        if(!admin.getPassword().equals(password)) {
            throw new PasswordMismatchException(ExceptionMessage.PASSWORD_MISMATCH);
        }

        return admin;
    }
}

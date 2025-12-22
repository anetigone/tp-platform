package com.tp.common.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Admin implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String realname;
    private String phone;
    private String email;
    private String avatar;
    //0:管理员 1:客服
    private Integer role;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

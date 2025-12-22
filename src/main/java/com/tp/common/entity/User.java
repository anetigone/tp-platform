package com.tp.common.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    // 0:禁用 1:正常
    private Integer status;

    private Integer credit;
    private BigDecimal balance;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
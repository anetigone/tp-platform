package com.tp.common.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String province;
    private String city;
    private String street;
    private String detail;
    private String zipCode;
    // 0:非默认 1:默认
    private Integer isDefault;
    // 0:禁用 1:正常
    private Integer status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

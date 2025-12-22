package com.tp.common.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Cart implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    // 1:正常 2:取消
    private Integer status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

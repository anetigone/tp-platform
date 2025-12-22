package com.tp.common.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private String icon;
    private Integer status;
    private Integer sort;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.tp.common.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private String name;
    private String description;
    private String icon;
    private Integer status;
    private Integer sort;
}

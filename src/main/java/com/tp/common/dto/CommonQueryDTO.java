package com.tp.common.dto;

import lombok.Data;

@Data
public class CommonQueryDTO {
    private Long id;
    private Integer page = 1;
    private Integer size = 20;
}

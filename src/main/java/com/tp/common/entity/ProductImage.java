package com.tp.common.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ProductImage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long productId;
    private String url;
    private String name;
    private String description;
}

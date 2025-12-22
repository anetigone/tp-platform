package com.tp.common.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CartItem implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long cartId;
    private Long productId;
    private String productName;
    private String productImage;
    private Integer quantity;
    private BigDecimal unit;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

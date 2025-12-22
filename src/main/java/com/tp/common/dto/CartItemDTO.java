package com.tp.common.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long userId;
    private Long cartId;
    private Long productId;
    private Integer quantity;
}

package com.tp.common.vo;

import com.tp.common.entity.Cart;
import com.tp.common.entity.CartItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartVO {
    private Cart cart;
    private List<CartItem> cartItems;
}

package com.tp.service.impl;

import com.tp.common.entity.CartItem;
import com.tp.common.exception.CartItemException;
import com.tp.common.exception.ExceptionMessage;
import com.tp.mapper.CartItemMapper;
import com.tp.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public boolean insert(CartItem cartItem) {
        if (cartItem == null || cartItem.getCartId() == null || cartItem.getProductId() == null) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_PRODUCT_ID_NULL);
        }

        if (cartItem.getQuantity() == null || cartItem.getQuantity() <= 0) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_QUANTITY_INVALID);
        }

        // 设置创建时间和更新时间
        cartItem.setCreateTime(LocalDateTime.now());
        cartItem.setUpdateTime(LocalDateTime.now());

        return cartItemMapper.insert(cartItem) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_ID_NULL);
        }

        return cartItemMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(CartItem cartItem) {
        if (cartItem == null || cartItem.getId() == null) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_ID_NULL);
        }

        if (cartItem.getQuantity() != null && cartItem.getQuantity() <= 0) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_QUANTITY_INVALID);
        }

        // 设置更新时间
        cartItem.setUpdateTime(LocalDateTime.now());

        return cartItemMapper.update(cartItem) > 0;
    }

    @Override
    public CartItem getById(Long id) {
        if (id == null) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_ID_NULL);
        }

        return cartItemMapper.getById(id);
    }

    @Override
    public List<CartItem> list(CartItem cartItem) {
        return cartItemMapper.getList(cartItem);
    }

    @Override
    public List<CartItem> listBatchIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_NOT_FOUND);
        }

        return cartItemMapper.getBatchIds(ids);
    }

    @Override
    public List<CartItem> listByCartId(Long cartId) {
        if (cartId == null) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_ID_NULL);
        }

        return cartItemMapper.getByCartId(cartId);
    }

    @Override
    public CartItem getByCartIdAndProductId(Long cartId, Long productId) {
        if (cartId == null || productId == null) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_PRODUCT_ID_NULL);
        }

        return cartItemMapper.getByCartIdAndProductId(cartId, productId);
    }

    @Override
    public boolean clearByCartId(Long cartId) {
        if (cartId == null) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_ID_NULL);
        }

        return cartItemMapper.deleteByCartId(cartId) > 0;
    }

    @Override
    public boolean updateQuantity(Long id, Integer quantity) {
        if (id == null || quantity == null || quantity <= 0) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_QUANTITY_INVALID);
        }

        return cartItemMapper.updateQuantity(id, quantity) > 0;
    }

    @Override
    public boolean addToCart(CartItem item) {
        Long cartId = item.getCartId();
        Long productId = item.getProductId();
        String productName = item.getProductName();
        String productImage = item.getProductImage();
        BigDecimal unit = item.getUnit();
        Integer quantity = item.getQuantity();
        if (cartId == null || productId == null || quantity == null || quantity <= 0) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_QUANTITY_INVALID);
        }

        // 查询是否已存在该商品的购物车项
        CartItem existingItem = cartItemMapper.getByCartIdAndProductId(cartId, productId);

        if (existingItem != null) {
            // 如果已存在，更新数量
            Integer newQuantity = existingItem.getQuantity() + quantity;
            return cartItemMapper.updateQuantity(existingItem.getId(), newQuantity) > 0;
        } else {
            // 如果不存在，创建新的购物车项
            CartItem newItem = CartItem.builder()
                    .cartId(cartId)
                    .productId(productId)
                    .productName(productName)
                    .productImage(productImage)
                    .quantity(quantity)
                    .unit(unit)
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();

            return cartItemMapper.insert(newItem) > 0;
        }
    }

    @Override
    public boolean removeFromCart(Long cartId, Long productId, Integer quantity) {
        if (cartId == null || productId == null || quantity == null || quantity <= 0) {
            throw new CartItemException(ExceptionMessage.CART_ITEM_QUANTITY_INVALID);
        }

        // 查询购物车项
        CartItem cartItem = cartItemMapper.getByCartIdAndProductId(cartId, productId);
        if (cartItem == null) {
            return false; // 购物车中不存在该商品
        }

        Integer currentQuantity = cartItem.getQuantity();

        if (currentQuantity <= quantity) {
            // 如果要删除的数量大于等于当前数量，直接删除该购物车项
            return cartItemMapper.deleteById(cartItem.getId()) > 0;
        } else {
            // 否则减少数量
            Integer newQuantity = currentQuantity - quantity;
            return cartItemMapper.updateQuantity(cartItem.getId(), newQuantity) > 0;
        }
    }

    @Override
    public List<CartItem> getByCartId(Long id) {
        if (id == null) {
            throw new CartItemException(ExceptionMessage.CART_ID_NULL);
        }
        return cartItemMapper.getByCartId(id);
    }
}
package com.tp.service.impl;

import com.tp.common.entity.Cart;
import com.tp.common.exception.CartException;
import com.tp.common.exception.ExceptionMessage;
import com.tp.mapper.CartMapper;
import com.tp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public boolean insert(Cart cart) {
        if (cart == null || cart.getUserId() == null) {
            throw new CartException(ExceptionMessage.CART_USER_ID_NULL);
        }

        // 设置创建时间和更新时间
        cart.setCreateTime(LocalDateTime.now());
        cart.setUpdateTime(LocalDateTime.now());

        // 设置默认状态为正常
        if (cart.getStatus() == null) {
            cart.setStatus(1);
        }

        return cartMapper.insert(cart) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            throw new CartException(ExceptionMessage.CART_NOT_FOUND);
        }

        return cartMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(Cart cart) {
        if (cart == null || cart.getId() == null) {
            throw new CartException(ExceptionMessage.CART_NOT_FOUND);
        }

        // 设置更新时间
        cart.setUpdateTime(LocalDateTime.now());

        return cartMapper.update(cart) > 0;
    }

    @Override
    public Cart getById(Long id) {
        if (id == null) {
            throw new CartException(ExceptionMessage.CART_NOT_FOUND);
        }

        return cartMapper.getById(id);
    }

    @Override
    public List<Cart> list(Cart cart) {
        return cartMapper.getList(cart);
    }

    @Override
    public List<Cart> listBatchIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new CartException(ExceptionMessage.CART_NOT_FOUND);
        }

        return cartMapper.getBatchIds(ids);
    }

    @Override
    public Cart getByUserId(Long userId) {
        if (userId == null) {
            throw new CartException(ExceptionMessage.CART_USER_ID_NULL);
        }

        return cartMapper.getByUserId(userId);
    }

    @Override
    public Cart getOrCreateCart(Long userId) {
        if (userId == null) {
            throw new CartException(ExceptionMessage.CART_USER_ID_NULL);
        }

        // 先查询用户是否已有购物车
        Cart existingCart = cartMapper.getByUserId(userId);
        if (existingCart != null) {
            return existingCart;
        }

        // 如果没有购物车，创建新的购物车
        Cart newCart = new Cart();
        newCart.setUserId(userId);
        newCart.setStatus(1);
        newCart.setCreateTime(LocalDateTime.now());
        newCart.setUpdateTime(LocalDateTime.now());

        cartMapper.insert(newCart);
        return newCart;
    }
}
package com.tp.service;

import com.tp.common.entity.Cart;

import java.util.List;

public interface CartService {

    /**
     * 新增购物车
     * @param cart 购物车信息
     * @return 操作结果
     */
    boolean insert(Cart cart);

    /**
     * 根据ID删除购物车
     * @param id 购物车ID
     * @return 操作结果
     */
    boolean deleteById(Long id);

    /**
     * 更新购物车
     * @param cart 购物车信息
     * @return 操作结果
     */
    boolean update(Cart cart);

    /**
     * 根据ID查询购物车
     * @param id 购物车ID
     * @return 购物车信息
     */
    Cart getById(Long id);

    /**
     * 多条件查询购物车列表
     * @param cart 查询条件
     * @return 购物车列表
     */
    List<Cart> list(Cart cart);

    /**
     * 根据ID批量查询购物车
     * @param ids 购物车ID列表
     * @return 购物车列表
     */
    List<Cart> listBatchIds(List<Long> ids);

    /**
     * 根据用户ID查询购物车
     * @param userId 用户ID
     * @return 购物车信息
     */
    Cart getByUserId(Long userId);

    /**
     * 为用户创建或获取购物车
     * @param userId 用户ID
     * @return 购物车信息
     */
    Cart getOrCreateCart(Long userId);
}
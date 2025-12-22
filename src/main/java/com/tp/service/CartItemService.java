package com.tp.service;

import com.tp.common.entity.CartItem;

import java.util.List;

public interface CartItemService {

    /**
     * 新增购物车项
     * @param cartItem 购物车项信息
     * @return 操作结果
     */
    boolean insert(CartItem cartItem);

    /**
     * 根据ID删除购物车项
     * @param id 购物车项ID
     * @return 操作结果
     */
    boolean deleteById(Long id);

    /**
     * 更新购物车项
     * @param cartItem 购物车项信息
     * @return 操作结果
     */
    boolean update(CartItem cartItem);

    /**
     * 根据ID查询购物车项
     * @param id 购物车项ID
     * @return 购物车项信息
     */
    CartItem getById(Long id);

    /**
     * 多条件查询购物车项列表
     * @param cartItem 查询条件
     * @return 购物车项列表
     */
    List<CartItem> list(CartItem cartItem);

    /**
     * 根据ID批量查询购物车项
     * @param ids 购物车项ID列表
     * @return 购物车项列表
     */
    List<CartItem> listBatchIds(List<Long> ids);

    /**
     * 根据购物车ID查询购物车项列表
     * @param cartId 购物车ID
     * @return 购物车项列表
     */
    List<CartItem> listByCartId(Long cartId);

    /**
     * 根据购物车ID和商品ID查询购物车项
     * @param cartId 购物车ID
     * @param productId 商品ID
     * @return 购物车项信息
     */
    CartItem getByCartIdAndProductId(Long cartId, Long productId);

    /**
     * 根据购物车ID清空购物车项
     * @param cartId 购物车ID
     * @return 操作结果
     */
    boolean clearByCartId(Long cartId);

    /**
     * 更新购物车项数量
     * @param id 购物车项ID
     * @param quantity 数量
     * @return 操作结果
     */
    boolean updateQuantity(Long id, Integer quantity);

    /**
     * 添加商品到购物车
     * @param cartItem 购物车项信息
     * @return 操作结果
     */
    boolean addToCart(CartItem cartItem);

    /**
     * 从购物车移除商品（减少数量或删除）
     * @param cartId 购物车ID
     * @param productId 商品ID
     * @param quantity 要减少的数量
     * @return 操作结果
     */
    boolean removeFromCart(Long cartId, Long productId, Integer quantity);

    /**
     * 根据购物车ID获取购物车项列表
     * @param id 购物车ID
     * @return 购物车项列表
     */
    List<CartItem> getByCartId(Long id);
}
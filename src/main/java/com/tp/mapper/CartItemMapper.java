package com.tp.mapper;

import com.tp.common.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartItemMapper {

    /**
     * 新增购物车项
     * @param cartItem 购物车项信息
     * @return 影响行数
     */
    int insert(CartItem cartItem);

    /**
     * 根据ID删除购物车项（逻辑删除）
     * @param id 购物车项ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 条件更新购物车项（动态更新）
     * @param cartItem 购物车项信息
     * @return 影响行数
     */
    int update(CartItem cartItem);

    /**
     * 根据ID查询购物车项
     * @param id 购物车项ID
     * @return 购物车项信息
     */
    CartItem getById(@Param("id") Long id);

    /**
     * 多条件查询购物车项列表
     * @param cartItem 查询条件
     * @return 购物车项列表
     */
    List<CartItem> getList(CartItem cartItem);

    /**
     * 根据ID批量查询购物车项
     * @param ids 购物车项ID列表
     * @return 购物车项列表
     */
    List<CartItem> getBatchIds(@Param("ids") List<Long> ids);

    /**
     * 根据购物车ID查询购物车项列表
     * @param cartId 购物车ID
     * @return 购物车项列表
     */
    List<CartItem> getByCartId(@Param("cartId") Long cartId);

    /**
     * 根据购物车ID和商品ID查询购物车项
     * @param cartId 购物车ID
     * @param productId 商品ID
     * @return 购物车项信息
     */
    CartItem getByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);

    /**
     * 根据购物车ID批量删除购物车项
     * @param cartId 购物车ID
     * @return 影响行数
     */
    int deleteByCartId(@Param("cartId") Long cartId);

    /**
     * 更新购物车项数量
     * @param id 购物车项ID
     * @param quantity 数量
     * @return 影响行数
     */
    int updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);
}
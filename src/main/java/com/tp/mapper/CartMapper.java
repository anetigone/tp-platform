package com.tp.mapper;

import com.tp.common.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {

    /**
     * 新增购物车
     * @param cart 购物车信息
     * @return 影响行数
     */
    int insert(Cart cart);

    /**
     * 根据ID删除购物车（逻辑删除）
     * @param id 购物车ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 条件更新购物车（动态更新）
     * @param cart 购物车信息
     * @return 影响行数
     */
    int update(Cart cart);

    /**
     * 根据ID查询购物车
     * @param id 购物车ID
     * @return 购物车信息
     */
    Cart getById(@Param("id") Long id);

    /**
     * 多条件查询购物车列表
     * @param cart 查询条件
     * @return 购物车列表
     */
    List<Cart> getList(Cart cart);

    /**
     * 根据ID批量查询购物车
     * @param ids 购物车ID列表
     * @return 购物车列表
     */
    List<Cart> getBatchIds(@Param("ids") List<Long> ids);

    /**
     * 根据用户ID查询购物车
     * @param userId 用户ID
     * @return 购物车信息
     */
    Cart getByUserId(@Param("userId") Long userId);
}
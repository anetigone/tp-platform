package com.tp.mapper;

import com.tp.common.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    /**
     * 新增订单项
     * @param orderItem 订单项信息
     * @return 影响行数
     */
    int insert(OrderItem orderItem);

    /**
     * 根据ID删除订单项（逻辑删除）
     * @param id 订单项ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 条件更新订单项（动态更新）
     * @param orderItem 订单项信息
     * @return 影响行数
     */
    int update(OrderItem orderItem);

    /**
     * 根据ID查询订单项
     * @param id 订单项ID
     * @return 订单项信息
     */
    OrderItem getById(@Param("id") Long id);

    /**
     * 多条件查询订单项列表
     * @param orderItem 查询条件
     * @return 订单项列表
     */
    List<OrderItem> getList(OrderItem orderItem);

    /**
     * 根据ID批量查询订单项
     * @param ids 订单项ID列表
     * @return 订单项列表
     */
    List<OrderItem> getBatchIds(@Param("ids") List<Long> ids);

    /**
     * 根据订单ID查询订单项列表
     * @param orderId 订单ID
     * @return 订单项列表
     */
    List<OrderItem> getByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据订单ID和商品ID查询订单项
     * @param orderId 订单ID
     * @param productId 商品ID
     * @return 订单项信息
     */
    OrderItem getByOrderIdAndProductId(@Param("orderId") Long orderId, @Param("productId") Long productId);

    /**
     * 根据订单ID批量删除订单项
     * @param orderId 订单ID
     * @return 影响行数
     */
    int deleteByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据商品ID查询订单项列表
     * @param productId 商品ID
     * @return 订单项列表
     */
    List<OrderItem> getByProductId(@Param("productId") Long productId);

    /**
     * 批量新增订单项
     * @param orderItems 订单项列表
     * @return 影响行数
     */
    int batchInsert(@Param("orderItems") List<OrderItem> orderItems);

    /**
     * 统计订单的商品总数
     * @param orderId 订单ID
     * @return 商品总数
     */
    Integer countTotalQuantity(@Param("orderId") Long orderId);

    /**
     * 根据订单ID统计订单项数量
     * @param orderId 订单ID
     * @return 订单项数量
     */
    Integer countByOrderId(@Param("orderId") Long orderId);
}
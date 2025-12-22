package com.tp.mapper;

import com.tp.common.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 新增订单
     * @param order 订单信息
     * @return 影响行数
     */
    int insert(Order order);

    /**
     * 根据ID删除订单（逻辑删除）
     * @param id 订单ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 条件更新订单（动态更新）
     * @param order 订单信息
     * @return 影响行数
     */
    int update(Order order);

    /**
     * 根据ID查询订单
     * @param id 订单ID
     * @return 订单信息
     */
    Order getById(@Param("id") Long id);

    /**
     * 根据订单号查询订单
     * @param orderNo 订单号
     * @return 订单信息
     */
    Order getByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 多条件查询订单列表
     * @param order 查询条件
     * @return 订单列表
     */
    List<Order> getList(Order order);

    /**
     * 根据ID批量查询订单
     * @param ids 订单ID列表
     * @return 订单列表
     */
    List<Order> getBatchIds(@Param("ids") List<Long> ids);

    /**
     * 根据买家ID查询订单列表
     * @param buyerId 买家ID
     * @return 订单列表
     */
    List<Order> getByBuyerId(@Param("buyerId") Long buyerId);

    /**
     * 根据卖家ID查询订单列表
     * @param sellerId 卖家ID
     * @return 订单列表
     */
    List<Order> getBySellerId(@Param("sellerId") Long sellerId);

    /**
     * 根据状态查询订单列表
     * @param status 订单状态
     * @return 订单列表
     */
    List<Order> getByStatus(@Param("status") Integer status);

    /**
     * 更新订单状态
     * @param id 订单ID
     * @param status 新状态
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新支付方式
     * @param id 订单ID
     * @param payMethod 支付方式
     * @return 影响行数
     */
    int updatePayMethod(@Param("id") Long id, @Param("payMethod") Integer payMethod);
}
package com.tp.service;

import com.tp.common.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    /**
     * 新增订单
     * @param order 订单信息
     * @return 操作结果
     */
    boolean insert(Order order);

    /**
     * 根据ID删除订单
     * @param id 订单ID
     * @return 操作结果
     */
    boolean deleteById(Long id);

    /**
     * 更新订单
     * @param order 订单信息
     * @return 操作结果
     */
    boolean update(Order order);

    /**
     * 根据ID查询订单
     * @param id 订单ID
     * @return 订单信息
     */
    Order getById(Long id);

    /**
     * 根据订单号查询订单
     * @param orderNo 订单号
     * @return 订单信息
     */
    Order getByOrderNo(String orderNo);

    /**
     * 多条件查询订单列表
     * @param order 查询条件
     * @return 订单列表
     */
    List<Order> list(Order order);

    /**
     * 根据ID批量查询订单
     * @param ids 订单ID列表
     * @return 订单列表
     */
    List<Order> listBatchIds(List<Long> ids);

    /**
     * 根据买家ID查询订单列表
     * @param buyerId 买家ID
     * @return 订单列表
     */
    List<Order> listByBuyerId(Long buyerId);

    /**
     * 根据卖家ID查询订单列表
     * @param sellerId 卖家ID
     * @return 订单列表
     */
    List<Order> listBySellerId(Long sellerId);

    /**
     * 根据状态查询订单列表
     * @param status 订单状态
     * @return 订单列表
     */
    List<Order> listByStatus(Integer status);

    /**
     * 更新订单状态
     * @param id 订单ID
     * @param status 新状态
     * @return 操作结果
     */
    boolean updateOrderStatus(Long id, Integer status);

    /**
     * 更新支付方式
     * @param id 订单ID
     * @param payMethod 支付方式
     * @return 操作结果
     */
    boolean updatePayMethod(Long id, Integer payMethod);

    /**
     * 创建订单
     * @param sellerId 卖家ID
     * @param buyerId 买家ID
     * @param addressId 收货地址ID
     * @param amount 订单金额
     * @param discount 折扣金额
     * @param remark 备注
     * @return 订单信息
     */
    Order createOrder(Long sellerId, Long buyerId, Long addressId, BigDecimal amount, BigDecimal discount, String remark);

    /**
     * 取消订单
     * @param id 订单ID
     * @return 操作结果
     */
    boolean cancelOrder(Long id);

    /**
     * 支付订单
     * @param id 订单ID
     * @param payMethod 支付方式
     * @return 操作结果
     */
    boolean payOrder(Long id, Integer payMethod);

    /**
     * 发货
     * @param id 订单ID
     * @return 操作结果
     */
    boolean shipOrder(Long id);

    /**
     * 确认收货
     * @param id 订单ID
     * @return 操作结果
     */
    boolean receiveOrder(Long id);

    /**
     * 申请退款
     * @param id 订单ID
     * @return 操作结果
     */
    boolean refundOrder(Long id);

    /**
     * 完成退款
     * @param id 订单ID
     * @return 操作结果
     */
    boolean completeRefund(Long id);

    /**
     * 生成订单号
     * @return 订单号
     */
    String generateOrderNo();
}
package com.tp.service;

import com.tp.common.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemService {

    /**
     * 新增订单项
     * @param orderItem 订单项信息
     * @return 操作结果
     */
    boolean insert(OrderItem orderItem);

    /**
     * 根据ID删除订单项
     * @param id 订单项ID
     * @return 操作结果
     */
    boolean deleteById(Long id);

    /**
     * 更新订单项
     * @param orderItem 订单项信息
     * @return 操作结果
     */
    boolean update(OrderItem orderItem);

    /**
     * 根据ID查询订单项
     * @param id 订单项ID
     * @return 订单项信息
     */
    OrderItem getById(Long id);

    /**
     * 多条件查询订单项列表
     * @param orderItem 查询条件
     * @return 订单项列表
     */
    List<OrderItem> list(OrderItem orderItem);

    /**
     * 根据ID批量查询订单项
     * @param ids 订单项ID列表
     * @return 订单项列表
     */
    List<OrderItem> listBatchIds(List<Long> ids);

    /**
     * 根据订单ID查询订单项列表
     * @param orderId 订单ID
     * @return 订单项列表
     */
    List<OrderItem> listByOrderId(Long orderId);

    /**
     * 根据订单ID和商品ID查询订单项
     * @param orderId 订单ID
     * @param productId 商品ID
     * @return 订单项信息
     */
    OrderItem getByOrderIdAndProductId(Long orderId, Long productId);

    /**
     * 根据商品ID查询订单项列表
     * @param productId 商品ID
     * @return 订单项列表
     */
    List<OrderItem> listByProductId(Long productId);

    /**
     * 根据订单ID清空订单项
     * @param orderId 订单ID
     * @return 操作结果
     */
    boolean clearByOrderId(Long orderId);

    /**
     * 批量新增订单项
     * @param orderItems 订单项列表
     * @return 操作结果
     */
    boolean batchInsert(List<OrderItem> orderItems);

    /**
     * 统计订单的商品总数
     * @param orderId 订单ID
     * @return 商品总数
     */
    Integer getTotalQuantity(Long orderId);

    /**
     * 根据订单ID统计订单项数量
     * @param orderId 订单ID
     * @return 订单项数量
     */
    Integer countByOrderId(Long orderId);

    /**
     * 创建订单项
     * @param orderId 订单ID
     * @param productId 商品ID
     * @param productName 商品名称
     * @param productImage 商品图片
     * @param unitPrice 单价
     * @param quantity 数量
     * @return 订单项信息
     */
    OrderItem createOrderItem(Long orderId, Long productId, String productName, String productImage, BigDecimal unitPrice, Integer quantity);

    /**
     * 为订单创建多个订单项
     * @param orderId 订单ID
     * @param orderItems 订单项列表（包含商品ID、单价、数量等信息）
     * @return 操作结果
     */
    boolean createOrderItemsForOrder(Long orderId, List<OrderItem> orderItems);

    /**
     * 计算订单项总价
     * @param unitPrice 单价
     * @param quantity 数量
     * @return 总价
     */
    BigDecimal calculateTotalPrice(BigDecimal unitPrice, Integer quantity);

    /**
     * 验证订单项数量
     * @param quantity 数量
     * @return 是否有效
     */
    boolean isValidQuantity(Integer quantity);
}
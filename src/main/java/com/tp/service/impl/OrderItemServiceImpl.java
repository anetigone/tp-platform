package com.tp.service.impl;

import com.tp.common.entity.OrderItem;
import com.tp.common.exception.ExceptionMessage;
import com.tp.common.exception.OrderItemException;
import com.tp.mapper.OrderItemMapper;
import com.tp.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public boolean insert(OrderItem orderItem) {
        if (orderItem == null || orderItem.getOrderId() == null || orderItem.getProductId() == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_ORDER_ID_NULL);
        }

        if (orderItem.getQuantity() == null || orderItem.getQuantity() <= 0) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_QUANTITY_INVALID);
        }

        if (orderItem.getUnitPrice() == null || orderItem.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_UNIT_PRICE_INVALID);
        }

        // 设置创建时间和更新时间
        orderItem.setCreateTime(LocalDateTime.now());
        orderItem.setUpdateTime(LocalDateTime.now());

        // 自动计算总价
        if (orderItem.getTotalPrice() == null) {
            orderItem.setTotalPrice(calculateTotalPrice(orderItem.getUnitPrice(), orderItem.getQuantity()));
        }

        return orderItemMapper.insert(orderItem) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_ID_NULL);
        }

        return orderItemMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(OrderItem orderItem) {
        if (orderItem == null || orderItem.getId() == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_ID_NULL);
        }

        if (orderItem.getQuantity() != null && orderItem.getQuantity() <= 0) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_QUANTITY_INVALID);
        }

        if (orderItem.getUnitPrice() != null && orderItem.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_UNIT_PRICE_INVALID);
        }

        // 设置更新时间
        orderItem.setUpdateTime(LocalDateTime.now());

        // 如果单价和数量都有值，自动计算总价
        if (orderItem.getUnitPrice() != null && orderItem.getQuantity() != null && orderItem.getTotalPrice() == null) {
            orderItem.setTotalPrice(calculateTotalPrice(orderItem.getUnitPrice(), orderItem.getQuantity()));
        }

        return orderItemMapper.update(orderItem) > 0;
    }

    @Override
    public OrderItem getById(Long id) {
        if (id == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_ID_NULL);
        }

        return orderItemMapper.getById(id);
    }

    @Override
    public List<OrderItem> list(OrderItem orderItem) {
        return orderItemMapper.getList(orderItem);
    }

    @Override
    public List<OrderItem> listBatchIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_NOT_FOUND);
        }

        return orderItemMapper.getBatchIds(ids);
    }

    @Override
    public List<OrderItem> listByOrderId(Long orderId) {
        if (orderId == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_ORDER_ID_NULL);
        }

        return orderItemMapper.getByOrderId(orderId);
    }

    @Override
    public OrderItem getByOrderIdAndProductId(Long orderId, Long productId) {
        if (orderId == null || productId == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_PRODUCT_ID_NULL);
        }

        return orderItemMapper.getByOrderIdAndProductId(orderId, productId);
    }

    @Override
    public List<OrderItem> listByProductId(Long productId) {
        if (productId == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_PRODUCT_ID_NULL);
        }

        return orderItemMapper.getByProductId(productId);
    }

    @Override
    public boolean clearByOrderId(Long orderId) {
        if (orderId == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_ORDER_ID_NULL);
        }

        return orderItemMapper.deleteByOrderId(orderId) > 0;
    }

    @Override
    public boolean batchInsert(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_LIST_EMPTY);
        }

        // 校验每个订单项
        for (OrderItem item : orderItems) {
            if (item.getOrderId() == null || item.getProductId() == null) {
                throw new OrderItemException(ExceptionMessage.ORDER_ITEM_PRODUCT_ID_NULL);
            }
            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new OrderItemException(ExceptionMessage.ORDER_ITEM_QUANTITY_INVALID);
            }
            if (item.getUnitPrice() == null || item.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new OrderItemException(ExceptionMessage.ORDER_ITEM_UNIT_PRICE_INVALID);
            }

            // 设置时间
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            // 计算总价
            if (item.getTotalPrice() == null) {
                item.setTotalPrice(calculateTotalPrice(item.getUnitPrice(), item.getQuantity()));
            }
        }

        return orderItemMapper.batchInsert(orderItems) > 0;
    }

    @Override
    public Integer getTotalQuantity(Long orderId) {
        if (orderId == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_ORDER_ID_NULL);
        }

        return orderItemMapper.countTotalQuantity(orderId);
    }

    @Override
    public Integer countByOrderId(Long orderId) {
        if (orderId == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_ORDER_ID_NULL);
        }

        return orderItemMapper.countByOrderId(orderId);
    }

    @Override
    public OrderItem createOrderItem(Long orderId, Long productId, String productName, String productImage, BigDecimal unitPrice, Integer quantity) {
        if (orderId == null || productId == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_PRODUCT_ID_NULL);
        }

        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_UNIT_PRICE_INVALID);
        }

        if (!isValidQuantity(quantity)) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_QUANTITY_INVALID);
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setProductId(productId);
        orderItem.setProductName(productName);
        orderItem.setProductImage(productImage);
        orderItem.setUnitPrice(unitPrice);
        orderItem.setQuantity(quantity);
        orderItem.setTotalPrice(calculateTotalPrice(unitPrice, quantity));
        orderItem.setCreateTime(LocalDateTime.now());
        orderItem.setUpdateTime(LocalDateTime.now());

        orderItemMapper.insert(orderItem);
        return orderItem;
    }

    @Override
    public boolean createOrderItemsForOrder(Long orderId, List<OrderItem> orderItems) {
        if (orderId == null) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_ORDER_ID_NULL);
        }

        if (orderItems == null || orderItems.isEmpty()) {
            throw new OrderItemException(ExceptionMessage.ORDER_ITEM_LIST_EMPTY);
        }

        // 设置订单ID到每个订单项
        for (OrderItem item : orderItems) {
            item.setOrderId(orderId);
        }

        return batchInsert(orderItems);
    }

    @Override
    public BigDecimal calculateTotalPrice(BigDecimal unitPrice, Integer quantity) {
        if (unitPrice == null || quantity == null || quantity <= 0) {
            return BigDecimal.ZERO;
        }

        // 单价 * 数量，保留2位小数
        return unitPrice.multiply(new BigDecimal(quantity)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public boolean isValidQuantity(Integer quantity) {
        return quantity != null && quantity > 0 && quantity <= 1000;
    }
}
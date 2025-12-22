package com.tp.service.impl;

import com.tp.common.context.BaseContext;
import com.tp.common.dto.OrderDTO;
import com.tp.common.entity.*;
import com.tp.common.exception.ExceptionMessage;
import com.tp.common.exception.OrderException;
import com.tp.mapper.*;
import com.tp.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CartMapper cartMapper;

    @Override
    public boolean insert(Order order) {
        if (order == null || order.getSellerId() == null || order.getBuyerId() == null) {
            throw new OrderException(ExceptionMessage.ORDER_USER_ID_NULL);
        }

        if (order.getAmount() == null || order.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderException(ExceptionMessage.ORDER_TOTAL_AMOUNT_INVALID);
        }

        // 设置创建时间和更新时间
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());

        // 设置默认值
        if (order.getDiscount() == null) {
            order.setDiscount(BigDecimal.ZERO);
        }
        if (order.getStatus() == null) {
            order.setStatus(0); // 待付款
        }
        if (order.getOrderNo() == null || order.getOrderNo().isEmpty()) {
            order.setOrderNo(generateOrderNo());
        }

        return orderMapper.insert(order) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            throw new OrderException(ExceptionMessage.ORDER_ID_NULL);
        }

        Order order = orderMapper.getById(id);
        if (order == null) {
            return false;
        }

        // 只有待付款状态的订单才能删除（取消）
        if (order.getStatus() != 0 && order.getStatus() != 4) {
            throw new OrderException(ExceptionMessage.ORDER_CANNOT_CANCEL);
        }

        return orderMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(Order order) {
        if (order == null || order.getId() == null) {
            throw new OrderException(ExceptionMessage.ORDER_ID_NULL);
        }

        if (order.getAmount() != null && order.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderException(ExceptionMessage.ORDER_TOTAL_AMOUNT_INVALID);
        }

        // 设置更新时间
        order.setUpdateTime(LocalDateTime.now());

        return orderMapper.update(order) > 0;
    }

    @Override
    public Order getById(Long id) {
        if (id == null) {
            throw new OrderException(ExceptionMessage.ORDER_ID_NULL);
        }

        return orderMapper.getById(id);
    }

    @Override
    public Order getByOrderNo(String orderNo) {
        if (orderNo == null || orderNo.trim().isEmpty()) {
            throw new OrderException(ExceptionMessage.ORDER_NOT_FOUND);
        }

        return orderMapper.getByOrderNo(orderNo);
    }

    @Override
    public List<Order> list(Order order) {
        return orderMapper.getList(order);
    }

    @Override
    public List<Order> listBatchIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new OrderException(ExceptionMessage.ORDER_NOT_FOUND);
        }

        return orderMapper.getBatchIds(ids);
    }

    @Override
    public List<Order> listByBuyerId(Long buyerId) {
        if (buyerId == null) {
            throw new OrderException(ExceptionMessage.ORDER_USER_ID_NULL);
        }

        return orderMapper.getByBuyerId(buyerId);
    }

    @Override
    public List<Order> listBySellerId(Long sellerId) {
        if (sellerId == null) {
            throw new OrderException(ExceptionMessage.ORDER_USER_ID_NULL);
        }

        return orderMapper.getBySellerId(sellerId);
    }

    @Override
    public List<Order> listByStatus(Integer status) {
        if (status == null) {
            throw new OrderException(ExceptionMessage.ORDER_STATUS_INVALID);
        }

        return orderMapper.getByStatus(status);
    }

    @Override
    public boolean updateOrderStatus(Long id, Integer status) {
        if (id == null || status == null) {
            throw new OrderException(ExceptionMessage.ORDER_STATUS_INVALID);
        }

        if (status < 0 || status > 7) {
            throw new OrderException(ExceptionMessage.ORDER_STATUS_INVALID);
        }

        Order order = orderMapper.getById(id);
        if (order == null) {
            return false;
        }

        // 验证状态流转的合法性
        if (!isValidStatusTransition(order.getStatus(), status)) {
            throw new OrderException(ExceptionMessage.ORDER_STATUS_INVALID);
        }

        return orderMapper.updateStatus(id, status) > 0;
    }

    @Override
    public boolean updatePayMethod(Long id, Integer payMethod) {
        if (id == null || payMethod == null) {
            throw new OrderException(ExceptionMessage.ORDER_PAYMENT_METHOD_INVALID);
        }

        if (payMethod < 0 || payMethod > 2) {
            throw new OrderException(ExceptionMessage.ORDER_PAYMENT_METHOD_INVALID);
        }

        Order order = orderMapper.getById(id);
        if (order == null) {
            return false;
        }

        // 只有待付款状态的订单才能设置支付方式
        if (order.getStatus() != 0) {
            throw new OrderException(ExceptionMessage.ORDER_CANNOT_PAY);
        }

        return orderMapper.updatePayMethod(id, payMethod) > 0;
    }

    @Override
    public Long createOrder(OrderDTO dto) {
        Long sellerId = dto.getSellerId();
        Long buyerId = dto.getBuyerId();
        Long addressId = dto.getAddressId();
        BigDecimal amount = dto.getAmount();
        BigDecimal discount = dto.getDiscount();
        String remark = dto.getRemark();
        List<Long> itemIds = dto.getCartItemIds();

        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OrderException(ExceptionMessage.ORDER_TOTAL_AMOUNT_INVALID);
        }

        String orderNo = generateOrderNo();
        Order order = Order.builder()
                .sellerId(sellerId)
                .buyerId(buyerId)
                .addressId(addressId)
                .amount(amount)
                .remark(remark)
                .discount(discount)
                .updateTime(LocalDateTime.now())
                .createTime(LocalDateTime.now())
                .orderNo(orderNo)
                .build();
        orderMapper.insert(order);

        List<CartItem> items = cartItemMapper.getBatchIds(itemIds);
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : items) {
            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getId())
                    .productId(item.getProductId())
                    .productName(item.getProductName())
                    .productImage(item.getProductImage())
                    .unitPrice(item.getUnit())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getUnit().multiply(new BigDecimal(item.getQuantity())))
                    .build();
            orderItems.add(orderItem);
        }
        orderItemMapper.batchInsert(orderItems);
        return order.getId();
    }

    @Override
    public boolean cancelOrder(Long id) {
        return updateOrderStatus(id, 4); // 已取消
    }

    @Override
    public boolean payOrder(Long id, Integer payMethod) {
        Order order = orderMapper.getById(id);
        if (order == null) {
            return false;
        }

        if (order.getStatus() != 0) {
            throw new OrderException(ExceptionMessage.ORDER_CANNOT_PAY);
        }

        // 先设置支付方式
        if (orderMapper.updatePayMethod(id, payMethod) <= 0) {
            return false;
        }

        switch (payMethod) {
            case 0:
            {
                Long userId = BaseContext.getCurrentUserId();
                User user = userMapper.getById(userId);
                if (user.getBalance().compareTo(order.getAmount()) < 0) {
                    throw new OrderException(ExceptionMessage.USER_BALANCE_NOT_ENOUGH);
                }
                user.setBalance(user.getBalance().subtract(order.getAmount()));
                userMapper.updateById(user);
                break;
            }
            case 1:
            {
                Long userId = BaseContext.getCurrentUserId();
                log.info("用户 {} 使用微信支付订单 {}", userId, id);
                break;
            }
            case 2:
            {
                Long userId = BaseContext.getCurrentUserId();
                log.info("用户 {} 使用支付宝支付订单 {}", userId, id);
                break;
            }
            default:
                throw new OrderException(ExceptionMessage.ORDER_PAYMENT_METHOD_INVALID);
        }
        boolean result = orderMapper.updateStatus(id, 1) > 0;
        if(result) {
            log.info("订单 {} 已支付", id);
            List<OrderItem> orderItems = orderItemMapper.getByOrderId(id);
            Long userId = BaseContext.getCurrentUserId();
            Cart cart = cartMapper.getByUserId(userId);
            Long cartId = cart.getId();
            List<Long> cartItemIds = new ArrayList<>();
            for(OrderItem orderItem : orderItems) {
                CartItem cartItem = cartItemMapper.getByCartIdAndProductId(cartId, orderItem.getProductId());
                cartItemIds.add(cartItem.getId());
            }
            cartItemMapper.deleteBatchIds(cartItemIds);
        }

        return result;
    }

    @Override
    public boolean shipOrder(Long id) {
        return updateOrderStatus(id, 2); // 待收货
    }

    @Override
    public boolean receiveOrder(Long id) {
        return updateOrderStatus(id, 3); // 已完成
    }

    @Override
    public boolean refundOrder(Long id) {
        return updateOrderStatus(id, 5); // 退款中
    }

    @Override
    public boolean completeRefund(Long id) {
        return updateOrderStatus(id, 6); // 已退款
    }

    @Override
    public String generateOrderNo() {
        // 格式：TP + 年月日时分秒 + 4位随机数
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        Random random = new Random();
        int randomNum = random.nextInt(10000);
        return "TP" + timestamp + String.format("%04d", randomNum);
    }

    /**
     * 验证订单状态流转是否合法
     * @param currentStatus 当前状态
     * @param newStatus 新状态
     * @return 是否合法
     */
    private boolean isValidStatusTransition(Integer currentStatus, Integer newStatus) {
        return switch (currentStatus) {
            case 0 -> // 待付款
                    newStatus == 1 || newStatus == 4 || newStatus == 7; // 待发货、已取消、交易关闭
            case 1 -> // 待发货
                    newStatus == 2 || newStatus == 5 || newStatus == 7; // 待收货、退款中、交易关闭
            case 2 -> // 待收货
                    newStatus == 3 || newStatus == 5; // 已完成、退款中
            case 3 -> // 已完成
                    newStatus == 5; // 退款中
            case 4 -> // 已取消
                    false; // 已取消不能改变状态
            case 5 -> // 退款中
                    newStatus == 6 || newStatus == 7; // 已退款、交易关闭
            case 6 -> // 已退款
                    false; // 已退款不能改变状态
            case 7 -> // 交易关闭
                    false; // 交易关闭不能改变状态
            default -> false;
        };
    }
}
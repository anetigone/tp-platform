package com.tp.controller;

import com.tp.common.context.BaseContext;
import com.tp.common.dto.OrderDTO;
import com.tp.common.entity.Order;
import com.tp.common.result.Result;
import com.tp.service.OrderItemService;
import com.tp.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    /**
     * 创建订单
     * @param dto 订单信息
     * @return 创建结果(订单ID)
     */
    @PostMapping("")
    public Result<Long> createOrder(@RequestBody OrderDTO dto) {
        Long id = orderService.createOrder(dto);
        return Result.success(id);
    }

    /**
     * 获取订单列表
     * @return 订单列表
     */
    @GetMapping("")
    public Result<List<Order>> list() {
        //todo 条件查询
        //todo 分页查询
        Long userId = BaseContext.getCurrentUserId();
        List<Order> orders = orderService.listByBuyerId(userId);

        return Result.success(orders);
    }

    /**
     * 获取订单详情
     * @param id 订单ID
     * @return 订单详情
     */
    @GetMapping("/{id}")
    public Result<Order> get(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return Result.success(order);
    }

    /**
     * 取消订单
     * @param id 订单ID
     * @return 取消结果
     */
    @PostMapping("/pay")
    public Result<String> pay(@RequestParam("id") Long id, @RequestParam("payMethod") Integer payMethod) {
        orderService.payOrder(id, payMethod);
        return Result.success();
    }

    /**
     * 发货
     * @param id 订单ID
     * @return 发货结果
     */
    @PostMapping("/ship")
    public Result<String> ship(@RequestParam("id") Long id) {
        orderService.shipOrder(id);
        return Result.success();
    }

    /**
     * 确认收货
     * @param id 订单ID
     * @return 确认收货结果
     */
    @PostMapping("/receive")
    public Result<String> receive(@RequestParam("id") Long id) {
        orderService.receiveOrder(id);
        return Result.success();
    }

    /**
     * 申请退款
     * @param id 订单ID
     * @return 申请结果
     */
    @PostMapping("/refund")
    public Result<String> refund(@RequestParam("id") Long id, @RequestParam("reason") String reason) {
        //todo 售后模块待开发
        orderService.refundOrder(id);
        return Result.success();
    }
}

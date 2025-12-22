package com.tp.common.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long sellerId;
    private Long buyerId;
    private Long addressId;
    private String orderNo;
    private String remark;

    private BigDecimal amount;
    private BigDecimal discount;
    // 0:待付款，1:待发货，2:待收货，3:已完成，4:已取消，5:退款中，6:已退款，7:交易关闭
    private Integer status;
    // 0:余额支付,1:微信支付,2:支付宝支付
    private Integer payMethod;


    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

package com.tp.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDTO {
    private Long sellerId;
    private Long buyerId;
    private Long addressId;
    private String remark;
    private List<Long> cartItemIds;
    private BigDecimal amount;
    private BigDecimal discount;
    private Integer payMethod;
}

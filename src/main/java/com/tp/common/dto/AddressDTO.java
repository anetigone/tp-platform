package com.tp.common.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long userId;
    private String province;
    private String city;
    private String street;
    private String detail;
    private String zipCode;
    // 0:非默认 1:默认
    private Integer isDefault;
    // 0:禁用 1:正常
    private Integer status;
}

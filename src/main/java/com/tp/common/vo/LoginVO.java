package com.tp.common.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVO {

    private String token;
    private String username;
    private Long id;
    // 0: admin, 1: user
    private int role;
}

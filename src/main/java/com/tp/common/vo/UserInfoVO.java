package com.tp.common.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoVO {
    private Long id;
    private String username;
    private String email;
    private String avatar;
}

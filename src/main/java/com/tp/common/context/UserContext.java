package com.tp.common.context;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserContext {
    private Long userId;
    private String username;
    private int role;
}


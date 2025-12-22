package com.tp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tp.mapper")
public class TpPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpPlatformApplication.class, args);
    }

}
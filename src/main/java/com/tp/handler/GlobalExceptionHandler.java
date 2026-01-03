package com.tp.handler;

import com.tp.common.exception.*;
import com.tp.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TpException.class)
    public Result<String> handleTpException(TpException e) {
        log.error("业务异常 {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(CartException.class)
    public Result<String> handleCartException(CartException e) {
        log.error("购物车异常 {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(CartItemException.class)
    public Result<String> handleCartItemException(CartItemException e) {
        log.error("购物车商品异常 {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(OrderException.class)
    public Result<String> handleOrderException(OrderException e) {
        log.error("订单异常 {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(OrderItemException.class)
    public Result<String> handleOrderItemException(OrderItemException e) {
        log.error("订单商品异常 {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(CommentException.class)
    public Result<String> handleCommentException(CommentException e) {
        log.error("评论异常 {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public Result<String> handleException(SQLException e) {
        log.error("数据库异常 {}", e.getMessage());
        return Result.error(e.getMessage());
    }
}

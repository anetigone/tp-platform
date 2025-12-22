package com.tp.common.result;

import lombok.Data;

import java.util.List;

/**
 * 分页结果封装类
 * @param <T> 数据类型
 */
@Data
public class PageResult<T> {
    private Integer code;
    private String msg;
    private List<T> list;
    private Long total;
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPages;

    public static <T> PageResult<T> success(List<T> list, Long total, Integer pageNo, Integer pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setCode(ResultCode.getSuccessCode());
        result.setMsg(ResultCode.getSuccessMsg());

        result.setList(list);
        result.setTotal(total);
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setTotalPages((int) Math.ceil((double) total / pageSize));

        return result;
    }

    public static <T> PageResult<T> error() {
        PageResult<T> result = new PageResult<>();
        result.setCode(ResultCode.getErrorCode());
        result.setMsg(ResultCode.getErrorMsg());
        return result;
    }
}
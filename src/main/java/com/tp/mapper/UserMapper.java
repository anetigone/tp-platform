package com.tp.mapper;

import com.tp.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    
    /**
     * 插入新用户
     * @param user 用户信息
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 根据用户ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    User getById(@Param("id") Long id);
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(@Param("username") String username);

    /**
     * 根据用户ID更新用户信息
     * @param user 用户信息
     * @return 影响行数
     */
    int updateById(User user);

    /**
     * 分页查询用户列表
     * @param offset 偏移量
     * @param limit 页大小
     * @return 用户列表
     */
    List<User> getPage(int offset, int limit);

    /**
     * 获取用户总数
     * @return 用户总数
     */
    long count();
}
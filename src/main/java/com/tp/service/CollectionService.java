package com.tp.service;

import com.tp.common.entity.Collects;

import java.util.List;

public interface CollectionService {
    /**
     * 创建收藏
     * @param collects 收藏
     * @return id
     */
    long create(Collects collects);

    /**
     * 获取收藏
     * @param id id
     * @return 收藏
     */
    Collects getById(Long id);

    /**
     * 获取所有收藏
     * @return 收藏列表
     */
    List<Collects> getAll();

    /**
     * 更新收藏
     * @param collects 收藏
     * @return 是否成功
     */
    boolean update(Collects collects);

    /**
     * 删除收藏
     * @param id id
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 获取用户收藏数量
     * @param userId 用户ID
     * @return 数量
     */
    long countByUserId(Long userId);

    /**
     * 获取用户收藏分页
     * @param userId 用户ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 收藏列表
     */
    List<Collects> getPageByUserId(Long userId, Integer page, Integer size);
}

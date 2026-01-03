package com.tp.service;

import com.tp.common.entity.Squat;

import java.util.List;

public interface SquatService {
    /**
     * 创建蹲蹲
     * @param squat 创建的蹲蹲信息
     * @return 创建结果
     */
    long create(Squat squat);

    /**
     * 根据ID查询蹲蹲
     * @param id 蹲蹲ID
     * @return 蹲蹲信息
     */
    Squat getById(Long id);

    /**
     * 获取所有蹲蹲
     * @return 蹲蹲列表
     */
    List<Squat> getAll();

    /**
     * 更新蹲蹲
     * @param squat 蹲蹲信息
     * @return 更新结果
     */
    boolean update(Squat squat);

    /**
     * 删除蹲蹲
     * @param id 蹲蹲ID
     * @return 删除结果
     */
    boolean deleteById(Long id);

    /**
     * 获取用户蹲蹲列表
     * @param userId 用户ID
     * @param offset 分页偏移
     * @param limit  分页数量
     * @return 蹲蹲列表
     */
    List<Squat> listByUserId(Long userId, Integer offset, Integer limit);

    /**
     * 获取用户蹲蹲数量
     * @param userId 用户ID
     * @return 蹲蹲数量
     */
    long countByUserId(Long userId);

    /**
     * 获取商品蹲蹲列表
     * @param productId 商品ID
     * @param offset    分页偏移
     * @param limit     分页数量
     * @return 蹲蹲列表
     */
    List<Squat> listByProductId(Long productId, Integer offset, Integer limit);

    /**
     * 获取商品蹲蹲数量
     * @param productId 商品ID
     * @return 蹲蹲数量
     */
    long countByProductId(Long productId);

    /**
     * 根据用户ID和商品ID获取蹲蹲
     * @param userId   用户ID
     * @param productId 商品ID
     * @return 蹲蹲信息
     */
    Squat getByUserIdAndProductId(Long userId, Long productId);
}

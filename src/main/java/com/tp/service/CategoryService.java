package com.tp.service;

import com.tp.common.entity.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 创建分类
     * @param category 分类信息
     * @return 创建结果
     */
    long create(Category category);

    /**
     * 根据ID查询分类
     * @param id 分类ID
     * @return 分类信息
     */
    Category getById(Long id);

    /**
     * 查询所有分类
     * @return 分类列表
     */
    List<Category> getAll();

    /**
     * 更新分类
     * @param category 分类信息
     * @return 更新结果
     */
    boolean update(Category category);

    /**
     * 删除分类
     * @param id 分类ID
     * @return 删除结果
     */
    boolean deleteById(Long id);
}

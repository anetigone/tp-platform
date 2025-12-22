package com.tp.mapper;

import com.tp.common.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    /**
     * 插入分类
     * @param category 分类信息
     * @return 影响行数
     */
    int insert(Category category);

    /**
     * 根据ID查询分类
     * @param id 分类ID
     * @return 分类信息
     */
    Category getById(@Param("id") Long id);

    /**
     * 查询所有分类
     * @return 分类列表
     */
    List<Category> getAll();

    /**
     * 更新分类
     * @param category 分类信息
     * @return 影响行数
     */
    int update(Category category);

    /**
     * 根据ID删除分类
     * @param id 分类ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
}

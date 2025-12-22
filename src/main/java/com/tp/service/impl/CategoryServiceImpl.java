package com.tp.service.impl;

import com.tp.common.entity.Category;
import com.tp.mapper.CategoryMapper;
import com.tp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public long create(Category category) {
        // 设置创建时间和更新时间
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        // 设置默认状态
        if (category.getStatus() == null) {
            category.setStatus(1); // 正常状态
        }

        categoryMapper.insert(category);

        return category.getId();
    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.getById(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryMapper.getAll();
    }

    @Override
    public boolean update(Category category) {
        // 设置更新时间
        category.setUpdateTime(LocalDateTime.now());

        return categoryMapper.update(category) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return categoryMapper.deleteById(id) > 0;
    }
}

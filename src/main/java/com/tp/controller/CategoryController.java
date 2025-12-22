package com.tp.controller;

import com.tp.common.dto.CategoryDTO;
import com.tp.common.entity.Category;
import com.tp.common.result.Result;
import com.tp.common.result.ResultMessage;
import com.tp.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获取所有分类
     * @return 分类列表
     */
    @GetMapping("")
    public List<Category> list() {
        return categoryService.getAll();
    }

    /**
     * 创建分类
     * @param dto 分类信息
     * @return 创建结果(分类ID)
     */
    @PostMapping("")
    public Result<Long> create(CategoryDTO dto) {
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        Long id = categoryService.create(category);

        return Result.success(id);
    }

    /**
     * 获取分类
     * @param id 分类ID
     * @return 分类信息
     */
    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    /**
     * 更新分类
     * @param dto 分类信息
     * @return 更新结果
     */
    @PostMapping("/{id}")
    public Result<String> update(CategoryDTO dto, @PathVariable Long id) {
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        category.setId(id);
        categoryService.update(category);
        return Result.success();
    }

    /**
     * 删除分类
     * @param id 分类ID
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return Result.success(ResultMessage.DELETE_SUCCESS);
    }
}

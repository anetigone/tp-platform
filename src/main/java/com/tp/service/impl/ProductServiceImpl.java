package com.tp.service.impl;

import com.tp.common.entity.Product;
import com.tp.mapper.ProductMapper;
import com.tp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Long create(Product product) {
        // 设置创建时间和更新时间
        product.setCreatTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());

        productMapper.insert(product);

        return product.getId();
    }

    @Override
    public Product getById(Long id) {
        return productMapper.getById(id);
    }

    @Override
    public List<Product> getAll() {
        return productMapper.getAll();
    }

    @Override
    public List<Product> getBySellerId(Long sellerId) {
        return productMapper.getBySellerId(sellerId);
    }

    @Override
    public List<Product> getPageBySellerId(Long sellerId, Integer page, Integer size) {
        return productMapper.getPageBySellerId(sellerId, page, size);
    }

    @Override
    public Long countBySellerId(Long sellerId) {
        return productMapper.countBySellerId(sellerId);
    }

    @Override
    public List<Product> getByCategoryId(Long categoryId) {
        return productMapper.getByCategoryId(categoryId);
    }

    @Override
    public boolean update(Product product) {
        // 设置更新时间
        product.setUpdateTime(LocalDateTime.now());

        return productMapper.update(product) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return productMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateQuantity(Long id, Integer quantity) {
        return productMapper.updateQuantity(id, quantity) > 0;
    }

    @Override
    public List<Product> getPage(Integer page, Integer size) {
        int offset = (page - 1) * size;
        int limit = size;
        return productMapper.getPage(offset, limit);
    }

    @Override
    public Long count() {
        return productMapper.count();
    }
}

package com.tp.service.impl;

import com.tp.common.entity.ProductImage;
import com.tp.mapper.ProductImageMapper;
import com.tp.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    public boolean create(ProductImage productImage) {
        return productImageMapper.insert(productImage) > 0;
    }

    @Override
    public ProductImage getById(Long id) {
        return productImageMapper.getById(id);
    }

    @Override
    public List<ProductImage> getByProductId(Long productId) {
        return productImageMapper.getByProductId(productId);
    }

    @Override
    public boolean update(ProductImage productImage) {
        return productImageMapper.update(productImage) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return productImageMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteByProductId(Long productId) {
        return productImageMapper.deleteByProductId(productId) > 0;
    }
}

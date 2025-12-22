package com.tp.service;

import com.tp.common.entity.ProductImage;

import java.util.List;

public interface ProductImageService {
    boolean create(ProductImage productImage);

    ProductImage getById(Long id);

    List<ProductImage> getByProductId(Long productId);

    boolean update(ProductImage productImage);

    boolean deleteById(Long id);

    boolean deleteByProductId(Long productId);
}

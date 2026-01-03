package com.tp.service;

import com.tp.common.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Long create(Product product);

    Product getById(Long id);

    List<Product> getAll();

    List<Product> getBySellerId(Long sellerId);

    List<Product> getPageBySellerId(Long sellerId, Integer page, Integer size);

    Long countBySellerId(Long sellerId);

    List<Product> getByCategoryId(Long categoryId);

    boolean update(Product product);

    boolean deleteById(Long id);

    boolean updateQuantity(Long id, Integer quantity);

    List<Product> getPage(Integer page, Integer size);

    Long count();

    List<Product> listByMap(Map<String, Object> map);

    Long countByMap(Map<String, Object> map);
}

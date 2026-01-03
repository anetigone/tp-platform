package com.tp.mapper;

import com.tp.common.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    int insert(Product product);

    Product getById(Long id);

    List<Product> getAll();

    List<Product> getBySellerId(Long sellerId);

    List<Product> getByCategoryId(Long categoryId);

    int update(Product product);

    int deleteById(Long id);

    int updateQuantity(Long id, Integer quantity);

    List<Product> getPage(int offset, int limit);

    List<Product> getPageBySellerId(Long sellerId, Integer offset, Integer limit);

    Long countBySellerId(Long sellerId);

    Long count();

    List<Product> listByMap(Map<String, Object> map);

    Long countByMap(Map<String, Object> map);
}

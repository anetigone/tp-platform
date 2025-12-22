package com.tp.mapper;

import com.tp.common.entity.ProductImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductImageMapper {
    int insert(ProductImage productImage);

    ProductImage getById(Long id);

    List<ProductImage> getByProductId(Long productId);

    int update(ProductImage productImage);

    int deleteById(Long id);

    int deleteByProductId(Long productId);
}

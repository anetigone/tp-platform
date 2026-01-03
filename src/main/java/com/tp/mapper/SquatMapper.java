package com.tp.mapper;

import com.tp.common.entity.Squat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SquatMapper {
    int insert(Squat squat);

    int update(Squat squat);

    int deleteById(Long id);

    Squat getById(Long id);

    List<Squat> getAll();

    List<Squat> listByUserId(Long userId, Integer offset, Integer limit);

    int countByUserId(Long userId);

    List<Squat> listByProductId(Long productId, Integer offset, Integer limit);

    int countByProductId(Long productId);

    Squat getByUserIdAndProductId(Long userId, Long productId);
}

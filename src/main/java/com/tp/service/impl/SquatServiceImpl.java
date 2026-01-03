package com.tp.service.impl;

import com.tp.common.entity.Squat;
import com.tp.common.exception.ExceptionMessage;
import com.tp.common.exception.SquatException;
import com.tp.mapper.SquatMapper;
import com.tp.service.SquatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SquatServiceImpl implements SquatService {

    private final SquatMapper squatMapper;

    public SquatServiceImpl(SquatMapper squatMapper) {
        this.squatMapper = squatMapper;
    }

    @Override
    public long create(Squat squat) {
        squatMapper.insert(squat);
        return squat.getId();
    }

    @Override
    public Squat getById(Long id) {
        if(id == null) {
            throw new SquatException(ExceptionMessage.SQUAT_ID_NULL);
        }

        return squatMapper.getById(id);
    }

    @Override
    public List<Squat> getAll() {
        return squatMapper.getAll();
    }

    @Override
    public boolean update(Squat squat) {
        if(squat.getId() == null) {
            throw new SquatException(ExceptionMessage.SQUAT_ID_NULL);
        }
        return squatMapper.update(squat) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        if(id == null) {
            throw new SquatException(ExceptionMessage.SQUAT_ID_NULL);
        }
        return squatMapper.deleteById(id) > 0;
    }

    @Override
    public List<Squat> listByUserId(Long userId, Integer offset, Integer limit) {
        if(userId == null) {
            throw new SquatException(ExceptionMessage.SQUAT_USER_ID_NULL);
        }
        return squatMapper.listByUserId(userId, offset, limit);
    }

    @Override
    public long countByUserId(Long userId) {
        if(userId == null) {
            throw new SquatException(ExceptionMessage.SQUAT_USER_ID_NULL);
        }
        return squatMapper.countByUserId(userId);
    }

    @Override
    public List<Squat> listByProductId(Long productId, Integer offset, Integer limit) {
        if(productId == null) {
            throw new SquatException(ExceptionMessage.SQUAT_PRODUCT_ID_NULL);
        }
        return squatMapper.listByProductId(productId, offset, limit);
    }

    @Override
    public long countByProductId(Long productId) {
        if(productId == null) {
            throw new SquatException(ExceptionMessage.SQUAT_PRODUCT_ID_NULL);
        }
        return squatMapper.countByProductId(productId);
    }

    @Override
    public Squat getByUserIdAndProductId(Long userId, Long productId) {
        if(userId == null || productId == null) {
            throw new SquatException(ExceptionMessage.SQUAT_USER_ID_NULL);
        }
        return squatMapper.getByUserIdAndProductId(userId, productId);
    }
}

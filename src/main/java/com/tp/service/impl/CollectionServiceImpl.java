package com.tp.service.impl;

import com.tp.common.entity.Collects;
import com.tp.mapper.CollectionMapper;
import com.tp.service.CollectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    private final CollectionMapper collectionMapper;

    public CollectionServiceImpl(CollectionMapper collectionMapper) {
        this.collectionMapper = collectionMapper;
    }

    @Override
    public long create(Collects collects) {
        collectionMapper.insert(collects);
        return collects.getId();
    }

    @Override
    public Collects getById(Long id) {
        return collectionMapper.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return collectionMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(Collects collects) {
        return collectionMapper.update(collects) > 0;
    }

    @Override
    public List<Collects> getAll() {
        return collectionMapper.getAll();
    }

    @Override
    public long countByUserId(Long userId) {
        return collectionMapper.countByUserId(userId);
    }

    @Override
    public List<Collects> getPageByUserId(Long userId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        int limit = size;
        return collectionMapper.getPageByUserId(userId, offset, limit);
    }
}

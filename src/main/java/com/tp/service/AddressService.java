package com.tp.service;

import com.tp.common.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressService {

    Address getById(Long id);

    Address getUserDefault(Long userId);

    List<Address> getByUserId(Long userId);

    List<Address> getPageByUserId(Long userId, Integer page, Integer size);

    Long countByUserId(Long userId);

    Long insert(Address address);

    boolean update(Address address);

    boolean deleteById(Long id);

    boolean setDefault(Long id, Long userId);
}

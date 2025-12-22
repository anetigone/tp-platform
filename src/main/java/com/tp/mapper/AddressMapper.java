package com.tp.mapper;

import com.tp.common.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {

    Address getById(Long id);

    List<Address> getByUserId(Long userId);

    int insert(Address address);

    boolean update(Address address);

    boolean updateBatch(List<Address> addresses);

    boolean deleteById(Long id);

    List<Address> getPageByUserId(Long userId, Integer offset, Integer limit);

    Long countByUserId(Long userId);
}

package com.tp.service.impl;

import com.tp.common.entity.Address;
import com.tp.common.exception.AddressException;
import com.tp.common.exception.ExceptionMessage;
import com.tp.mapper.AddressMapper;
import com.tp.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Address getById(Long id) {
        if (id == null) {
            throw new AddressException(ExceptionMessage.ADDRESS_ID_NULL);
        }
        return addressMapper.getById(id);
    }

    @Override
    public Address getUserDefault(Long userId) {
        List<Address> addresses = getByUserId(userId);
        for (Address address : addresses) {
            if(address.getIsDefault() == 1) {
                return address;
            }
        }
        throw new AddressException(ExceptionMessage.NO_DEFAULT_ADDRESS);
    }

    @Override
    public List<Address> getByUserId(Long userId) {
        if(userId == null) {
            throw new AddressException(ExceptionMessage.ADDRESS_USER_ID_NULL);
        }
        return addressMapper.getByUserId(userId);
    }

    @Override
    public List<Address> getPageByUserId(Long userId, Integer page, Integer size) {
        if(userId == null) {
            throw new AddressException(ExceptionMessage.ADDRESS_USER_ID_NULL);
        }
        return addressMapper.getPageByUserId(userId, page, size);
    }

    @Override
    public Long countByUserId(Long userId) {
        return addressMapper.countByUserId(userId);
    }

    @Override
    public Long insert(Address address) {
        List<Address> addresses = getByUserId(address.getUserId());
        if(addresses.isEmpty()) {
            address.setIsDefault(1);
        }
        addressMapper.insert(address);
        return address.getId();
    }

    @Override
    public boolean update(Address address) {
        if(address.getIsDefault() == 1) {
            List<Address> addresses = getByUserId(address.getUserId());
            for (Address address1 : addresses) {
                address.setIsDefault(0);
            }
            addressMapper.updateBatch(addresses);
        }

        return addressMapper.update(address);
    }

    @Override
    public boolean deleteById(Long id) {
        return addressMapper.deleteById(id);
    }

    @Override
    public boolean setDefault(Long id, Long userId) {
        List<Address> addresses = getByUserId(userId);
        for (Address address : addresses) {
            address.setIsDefault(0);
            if(address.getId().equals(id)) {
                address.setIsDefault(1);
            }
        }

        return addressMapper.updateBatch(addresses);
    }
}

package com.tp.controller;

import com.tp.common.context.BaseContext;
import com.tp.common.dto.AddressDTO;
import com.tp.common.entity.Address;
import com.tp.common.result.PageResult;
import com.tp.common.result.Result;
import com.tp.common.result.ResultMessage;
import com.tp.service.AddressService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * 根据ID查询地址
     * @param id 地址ID
     * @return 地址信息
     */
    @GetMapping("/{id}")
    public Result<Address> getById(@PathVariable("id") Long id) {
        Address address = addressService.getById(id);
        return Result.success(address);
    }

    /**
     * 根据用户ID查询地址列表
     * @param page 页码
     * @param size 页大小
     * @return 地址列表
     */
    @GetMapping("")
    public PageResult<Address> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Long userId = BaseContext.getCurrentUserId();
        List<Address> addresses = addressService.getPageByUserId(userId, page, size);
        Long total = addressService.countByUserId(userId);

        return PageResult.success(addresses, total, page, size);
    }

    /**
     * 根据用户ID查询默认地址
     * @return 默认地址信息
     */
    @GetMapping("/default")
    public Result<Address> getDefaultAddress() {
        Long userId = BaseContext.getCurrentUserId();
        Address address = addressService.getUserDefault(userId);
        return Result.success(address);
    }

    /**
     * 设置默认地址
     * @param id 地址ID
     */
    @PostMapping("/default/{id}")
    public Result<String> setDefaultAddress(@PathVariable("id") Long id) {
        Long userId = BaseContext.getCurrentUserId();
        addressService.setDefault(id, userId);

        return Result.success(ResultMessage.SET_DEFAULT_SUCCESS);
    }

    /**
     * 添加地址
     * @param dto 地址信息
     * @return 添加结果(添加成功后的地址ID)
     */
    @PostMapping("")
    public Result<Long> addAddress(@RequestBody AddressDTO dto) {
        Address address = new Address();
        BeanUtils.copyProperties(dto, address);
        Long id = addressService.insert(address);

        return Result.success(id);
    }

    /**
     * 修改地址
     * @param dto 地址信息
     * @return 修改结果
     */
    @PostMapping("/update/{id}")
    public Result<String> updateAddress(@PathVariable("id") Long id, @RequestBody AddressDTO dto) {
        Address address = new Address();
        BeanUtils.copyProperties(dto, address);
        address.setId(id);
        addressService.update(address);

        return Result.success(ResultMessage.UPDATE_SUCCESS);
    }

    /**
     * 删除地址
     * @param id 地址ID
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    public Result<String> deleteAddress(@PathVariable("id") Long id) {
        addressService.deleteById(id);

        return Result.success(ResultMessage.DELETE_SUCCESS);
    }
}

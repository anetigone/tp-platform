package com.tp.controller;

import com.tp.common.dto.SquatDTO;
import com.tp.common.entity.Squat;
import com.tp.common.result.PageResult;
import com.tp.common.result.Result;
import com.tp.service.SquatService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/squat")
public class SquatController {

    private final SquatService squatService;

    public SquatController(SquatService squatService) {
        this.squatService = squatService;
    }

    @PostMapping("")
    public Result<Long> create(SquatDTO dto) {
        Squat squat = new Squat();
        BeanUtils.copyProperties(dto, squat);
        Long id = squatService.create(squat);
        return Result.success(id);
    }

    @GetMapping("/{id}")
    public Result<Squat> getById(@PathVariable Long id) {
        Squat squat = squatService.getById(id);
        return Result.success(squat);
    }

    @PostMapping("/update/{id}")
    public Result<String> update(@PathVariable Long id, @RequestBody SquatDTO dto) {
        Squat squat = new Squat();
        BeanUtils.copyProperties(dto, squat);
        squat.setId(id);
        squatService.update(squat);
        return Result.success();
    }

    @GetMapping("/user")
    public PageResult<Squat> listByUserId(@RequestParam Long userId,
                                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<Squat> squats = squatService.listByUserId(userId, page, size);
        Long total = squatService.countByUserId(userId);
        return PageResult.success(squats, total, page, size);
    }

    @GetMapping("/product")
    public PageResult<Squat> listByProductId(@RequestParam Long productId,
                                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<Squat> squats = squatService.listByProductId(productId, page, size);
        Long total = squatService.countByProductId(productId);
        return PageResult.success(squats, total, page, size);
    }

    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        squatService.deleteById(id);
        return Result.success();
    }
}

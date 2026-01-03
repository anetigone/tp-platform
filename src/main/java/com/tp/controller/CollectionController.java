package com.tp.controller;

import com.tp.common.context.BaseContext;
import com.tp.common.entity.Collects;
import com.tp.common.result.PageResult;
import com.tp.common.result.Result;
import com.tp.service.CollectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/collection")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("")
    public PageResult<Collects> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Long userId = BaseContext.getCurrentUserId();
        List<Collects> collects = collectionService.getPageByUserId(userId, page, size);
        Long total = collectionService.countByUserId(userId);
        return PageResult.success(collects, total, page, size);
    }

    @PostMapping("")
    public Result<Long> create(@RequestBody Collects collects) {
        Long id = collectionService.create(collects);
        return Result.success(id);
    }

    @GetMapping("/{id}")
    public Result<Collects> getById(@PathVariable Long id) {
        Collects collects = collectionService.getById(id);
        return Result.success(collects);
    }

    @PostMapping("/cancel/{id}")
    public Result<String> cancel(@PathVariable Long id) {
        boolean result = collectionService.deleteById(id);
        return Result.success();
    }
}

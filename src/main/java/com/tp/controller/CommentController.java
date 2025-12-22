package com.tp.controller;

import com.tp.common.dto.CommentDTO;
import com.tp.common.entity.Comment;
import com.tp.common.result.PageResult;
import com.tp.common.result.Result;
import com.tp.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 创建商品评价
     * @param dto 商品评价信息
     * @return 创建结果(商品评价ID)
     */
    @PostMapping("")
    public Result<Long> create(@RequestBody CommentDTO dto) {
        Long id = commentService.createProductComment(dto);
        return Result.success(id);
    }

    /**
     * 获取指定商品评价列表
     * @param id 商品ID
     * @return 商品评价列表
     */
    @GetMapping("/product/{id}")
    public PageResult<Comment> getByProductId(@PathVariable Long id,
                                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<Comment> list = commentService.getPageByProductId(id, page, size);
        Long total = Long.valueOf(commentService.countByProductId(id));
        return PageResult.success(list, total, page, size);
    }


    /**
     * 更新商品评价
     * @param id 评价ID
     * @param dto 评价信息
     * @return 更新结果
     */
    @PostMapping("/update/{id}")
    public Result<String> update(@PathVariable Long id, @RequestBody CommentDTO dto) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(dto, comment);
        comment.setId(id);
        comment.setUpdateTime(LocalDateTime.now());
        commentService.update(comment);
        return Result.success();
    }

    /**
     * 删除商品评价
     * @param id 评价ID
     * @return 删除结果
     */
    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        commentService.deleteById(id);
        return Result.success();
    }
}

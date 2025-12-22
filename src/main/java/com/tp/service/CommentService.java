package com.tp.service;

import com.tp.common.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {

    /**
     * 新增评论
     * @param comment 评论信息
     * @return 操作结果
     */
    boolean insert(Comment comment);

    /**
     * 根据ID删除评论
     * @param id 评论ID
     * @return 操作结果
     */
    boolean deleteById(Long id);

    /**
     * 更新评论
     * @param comment 评论信息
     * @return 操作结果
     */
    boolean update(Comment comment);

    /**
     * 根据ID查询评论
     * @param id 评论ID
     * @return 评论信息
     */
    Comment getById(Long id);

    /**
     * 多条件查询评论列表
     * @param comment 查询条件
     * @return 评论列表
     */
    List<Comment> list(Comment comment);

    /**
     * 根据ID批量查询评论
     * @param ids 评论ID列表
     * @return 评论列表
     */
    List<Comment> listBatchIds(List<Long> ids);

    /**
     * 根据用户ID查询评论列表
     * @param userId 用户ID
     * @return 评论列表
     */
    List<Comment> listByUserId(Long userId);

    /**
     * 根据订单ID查询评论列表
     * @param orderId 订单ID
     * @return 评论列表
     */
    List<Comment> listByOrderId(Long orderId);

    /**
     * 根据商品ID查询评论列表
     * @param productId 商品ID
     * @return 评论列表
     */
    List<Comment> listByProductId(Long productId);

    /**
     * 根据商品ID和星级查询评论列表
     * @param productId 商品ID
     * @param star 星级
     * @return 评论列表
     */
    List<Comment> listByProductIdAndStar(Long productId, Integer star);

    /**
     * 根据商品ID查询评论统计信息
     * @param productId 商品ID
     * @return 统计信息（包含平均星级、评论总数等）
     */
    Map<String, Object> getCommentStatisticsByProductId(Long productId);

    /**
     * 创建商品评论
     * @param userId 用户ID
     * @param orderId 订单ID
     * @param productId 商品ID
     * @param content 评论内容
     * @param star 星级（1-5）
     * @return 操作结果
     */
    boolean createProductComment(Long userId, Long orderId, Long productId, String content, Integer star);

    /**
     * 检查用户是否已评论该商品
     * @param userId 用户ID
     * @param orderId 订单ID
     * @param productId 商品ID
     * @return 是否已评论
     */
    boolean hasCommented(Long userId, Long orderId, Long productId);

    /**
     * 根据订单ID删除所有评论
     * @param orderId 订单ID
     * @return 操作结果
     */
    boolean deleteByOrderId(Long orderId);

    /**
     * 验证星级是否有效
     * @param star 星级
     * @return 是否有效
     */
    boolean isValidStar(Integer star);

    /**
     * 验证评论内容是否有效
     * @param content 评论内容
     * @return 是否有效
     */
    boolean isValidContent(String content);

    /**
     * 获取商品的高星级评论（4-5星）
     * @param productId 商品ID
     * @return 评论列表
     */
    List<Comment> getHighStarComments(Long productId);

    /**
     * 获取商品的低星级评论（1-2星）
     * @param productId 商品ID
     * @return 评论列表
     */
    List<Comment> getLowStarComments(Long productId);
}
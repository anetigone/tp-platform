package com.tp.mapper;

import com.tp.common.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    /**
     * 新增评论
     * @param comment 评论信息
     * @return 影响行数
     */
    int insert(Comment comment);

    /**
     * 根据ID删除评论（物理删除）
     * @param id 评论ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 条件更新评论（动态更新）
     * @param comment 评论信息
     * @return 影响行数
     */
    int update(Comment comment);

    /**
     * 根据ID查询评论
     * @param id 评论ID
     * @return 评论信息
     */
    Comment getById(@Param("id") Long id);

    /**
     * 多条件查询评论列表
     * @param comment 查询条件
     * @return 评论列表
     */
    List<Comment> getList(Comment comment);

    /**
     * 根据ID批量查询评论
     * @param ids 评论ID列表
     * @return 评论列表
     */
    List<Comment> getBatchIds(@Param("ids") List<Long> ids);

    /**
     * 根据用户ID查询评论列表
     * @param userId 用户ID
     * @return 评论列表
     */
    List<Comment> getByUserId(@Param("userId") Long userId);

    /**
     * 根据订单ID查询评论列表
     * @param orderId 订单ID
     * @return 评论列表
     */
    List<Comment> getByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据商品ID查询评论列表
     * @param productId 商品ID
     * @return 评论列表
     */
    List<Comment> getByProductId(@Param("productId") Long productId);

    /**
     * 根据商品ID和星级查询评论列表
     * @param productId 商品ID
     * @param star 星级
     * @return 评论列表
     */
    List<Comment> getByProductIdAndStar(@Param("productId") Long productId, @Param("star") Integer star);

    /**
     * 统计商品的平均星级
     * @param productId 商品ID
     * @return 平均星级
     */
    Double getAverageStarByProductId(@Param("productId") Long productId);

    /**
     * 统计商品的评论总数
     * @param productId 商品ID
     * @return 评论总数
     */
    Integer countByProductId(@Param("productId") Long productId);

    /**
     * 根据星级统计商品评论数量
     * @param productId 商品ID
     * @return 星级统计结果（key:星级, value:数量）
     */
    List<java.util.Map<String, Object>> countStarDistributionByProductId(@Param("productId") Long productId);

    /**
     * 检查用户是否已评论该订单的商品
     * @param userId 用户ID
     * @param orderId 订单ID
     * @param productId 商品ID
     * @return 评论信息
     */
    Comment getByUserOrderProduct(@Param("userId") Long userId, @Param("orderId") Long orderId, @Param("productId") Long productId);

    /**
     * 根据订单ID删除所有评论
     * @param orderId 订单ID
     * @return 影响行数
     */
    int deleteByOrderId(@Param("orderId") Long orderId);
}
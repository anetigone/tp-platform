package com.tp.service.impl;

import com.tp.common.dto.CommentDTO;
import com.tp.common.entity.Comment;
import com.tp.common.exception.CommentException;
import com.tp.common.exception.ExceptionMessage;
import com.tp.mapper.CommentMapper;
import com.tp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public boolean insert(Comment comment) {
        if (comment == null || comment.getUserId() == null || comment.getProductId() == null) {
            throw new CommentException(ExceptionMessage.COMMENT_USER_ID_NULL);
        }

        if (!isValidContent(comment.getContent())) {
            throw new CommentException(ExceptionMessage.COMMENT_CONTENT_TOO_LONG);
        }

        if (!isValidStar(comment.getStar())) {
            throw new CommentException(ExceptionMessage.COMMENT_RATING_INVALID);
        }

        // 设置创建时间和更新时间
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());

        return commentMapper.insert(comment) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            throw new CommentException(ExceptionMessage.COMMENT_ID_NULL);
        }

        return commentMapper.deleteById(id) > 0;
    }

    @Override
    public boolean update(Comment comment) {
        if (comment == null || comment.getId() == null) {
            throw new CommentException(ExceptionMessage.COMMENT_ID_NULL);
        }

        if (comment.getContent() != null && !isValidContent(comment.getContent())) {
            throw new CommentException(ExceptionMessage.COMMENT_CONTENT_TOO_LONG);
        }

        if (comment.getStar() != null && !isValidStar(comment.getStar())) {
            throw new CommentException(ExceptionMessage.COMMENT_RATING_INVALID);
        }

        // 设置更新时间
        comment.setUpdateTime(LocalDateTime.now());

        return commentMapper.update(comment) > 0;
    }

    @Override
    public Comment getById(Long id) {
        if (id == null) {
            throw new CommentException(ExceptionMessage.COMMENT_ID_NULL);
        }

        return commentMapper.getById(id);
    }

    @Override
    public List<Comment> list(Comment comment) {
        return commentMapper.getList(comment);
    }

    @Override
    public List<Comment> listBatchIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new CommentException(ExceptionMessage.COMMENT_NOT_FOUND);
        }

        return commentMapper.getBatchIds(ids);
    }

    @Override
    public List<Comment> listByUserId(Long userId) {
        if (userId == null) {
            throw new CommentException(ExceptionMessage.COMMENT_USER_ID_NULL);
        }

        return commentMapper.getByUserId(userId);
    }

    @Override
    public List<Comment> listByOrderId(Long orderId) {
        if (orderId == null) {
            throw new CommentException(ExceptionMessage.COMMENT_NOT_FOUND);
        }

        return commentMapper.getByOrderId(orderId);
    }

    @Override
    public List<Comment> listByProductId(Long productId) {
        if (productId == null) {
            throw new CommentException(ExceptionMessage.COMMENT_PRODUCT_ID_NULL);
        }

        return commentMapper.getByProductId(productId);
    }

    @Override
    public List<Comment> listByProductIdAndStar(Long productId, Integer star) {
        if (productId == null) {
            throw new CommentException(ExceptionMessage.COMMENT_PRODUCT_ID_NULL);
        }

        if (!isValidStar(star)) {
            throw new CommentException(ExceptionMessage.COMMENT_RATING_INVALID);
        }

        return commentMapper.getByProductIdAndStar(productId, star);
    }

    @Override
    public Map<String, Object> getCommentStatisticsByProductId(Long productId) {
        if (productId == null) {
            throw new CommentException(ExceptionMessage.COMMENT_PRODUCT_ID_NULL);
        }

        Map<String, Object> statistics = new HashMap<>();

        // 获取平均星级
        Double averageStar = commentMapper.getAverageStarByProductId(productId);
        statistics.put("averageStar", averageStar != null ? averageStar : 0.0);

        // 获取评论总数
        Integer totalCount = commentMapper.countByProductId(productId);
        statistics.put("totalCount", totalCount != null ? totalCount : 0);

        // 获取星级分布
        List<Map<String, Object>> starDistribution = commentMapper.countStarDistributionByProductId(productId);
        statistics.put("starDistribution", starDistribution);

        // 计算好评率（4星及以上为好评）
        int goodCount = 0;
        int total = 0;
        for (Map<String, Object> starData : starDistribution) {
            Integer starValue = (Integer) starData.get("starValue");
            Long count = ((Number) starData.get("count")).longValue();

            total += count;
            if (starValue != null && starValue >= 4) {
                goodCount += count;
            }
        }

        double goodRate = total > 0 ? (double) goodCount / total * 100 : 0.0;
        statistics.put("goodRate", Math.round(goodRate * 100.0) / 100.0); // 保留两位小数

        return statistics;
    }

    @Override
    public Long createProductComment(CommentDTO dto) {
        Long userId = dto.getUserId();
        Long orderId = dto.getOrderId();
        Long productId = dto.getProductId();
        String content = dto.getContent();
        Integer star = dto.getStar();

        if (userId == null || orderId == null || productId == null) {
            throw new CommentException(ExceptionMessage.COMMENT_USER_ID_NULL);
        }

        if (!isValidContent(content)) {
            throw new CommentException(ExceptionMessage.COMMENT_CONTENT_TOO_LONG);
        }

        if (!isValidStar(star)) {
            throw new CommentException(ExceptionMessage.COMMENT_RATING_INVALID);
        }

        // 检查是否已评论
        if (hasCommented(userId, orderId, productId)) {
            throw new CommentException(ExceptionMessage.COMMENT_ALREADY_EXISTS);
        }

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setOrderId(orderId);
        comment.setProductId(productId);
        comment.setContent(content);
        comment.setStar(star);
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());

        commentMapper.insert(comment);

        return comment.getId();
    }

    @Override
    public boolean hasCommented(Long userId, Long orderId, Long productId) {
        if (userId == null || orderId == null || productId == null) {
            return false;
        }

        Comment comment = commentMapper.getByUserOrderProduct(userId, orderId, productId);
        return comment != null;
    }

    @Override
    public boolean deleteByOrderId(Long orderId) {
        if (orderId == null) {
            throw new CommentException(ExceptionMessage.COMMENT_NOT_FOUND);
        }

        return commentMapper.deleteByOrderId(orderId) > 0;
    }

    @Override
    public boolean isValidStar(Integer star) {
        return star != null && star >= 1 && star <= 5;
    }

    @Override
    public boolean isValidContent(String content) {
        return content != null && !content.trim().isEmpty() && content.length() <= 1000;
    }

    @Override
    public List<Comment> getHighStarComments(Long productId) {
        if (productId == null) {
            throw new CommentException(ExceptionMessage.COMMENT_PRODUCT_ID_NULL);
        }

        // 获取5星评论
        List<Comment> fiveStarComments = commentMapper.getByProductIdAndStar(productId, 5);
        // 获取4星评论
        List<Comment> fourStarComments = commentMapper.getByProductIdAndStar(productId, 4);

        // 合并并返回
        fiveStarComments.addAll(fourStarComments);
        return fiveStarComments;
    }

    @Override
    public List<Comment> getLowStarComments(Long productId) {
        if (productId == null) {
            throw new CommentException(ExceptionMessage.COMMENT_PRODUCT_ID_NULL);
        }

        // 获取1星评论
        List<Comment> oneStarComments = commentMapper.getByProductIdAndStar(productId, 1);
        // 获取2星评论
        List<Comment> twoStarComments = commentMapper.getByProductIdAndStar(productId, 2);

        // 合并并返回
        oneStarComments.addAll(twoStarComments);
        return oneStarComments;
    }

    @Override
    public List<Comment> getPageByProductId(Long id, Integer page, Integer size) {
        int offset = (page - 1) * size;
        int limit = size;
        return commentMapper.getPageByProductId(id, offset, limit);
    }

    @Override
    public Integer countByProductId(Long id) {
        return commentMapper.countByProductId(id);
    }
}
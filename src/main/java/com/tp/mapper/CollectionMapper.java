package com.tp.mapper;

import com.tp.common.entity.Collects;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionMapper {

    /**
     * 添加收藏
     * @param collects 收藏
     * @return 影响行数
     */
    int insert(Collects collects);

    /**
     * 根据id查询收藏
     * @param id 收藏ID
     * @return 收藏
     */
    Collects getById(Long id);

    /**
     * 查询所有收藏
     * @return 收藏列表
     */
    List<Collects> getAll();

    /**
     * 更新收藏
     * @param collects 收藏
     * @return 影响行数
     */
    int update(Collects collects);

    /**
     * 根据id删除收藏
     * @param id 收藏ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据用户id查询收藏数量
     * @param userId 用户ID
     * @return 收藏数量
     */
    long countByUserId(Long userId);

    /**
     * 根据用户id查询收藏
     * @param userId 用户ID
     * @return 收藏列表
     */
    List<Collects> getByUserId(Long userId);

    /**
     * 根据用户id分页查询收藏
     * @param userId 用户ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 收藏列表
     */
    List<Collects> getPageByUserId(Long userId, Integer offset, Integer limit);
}

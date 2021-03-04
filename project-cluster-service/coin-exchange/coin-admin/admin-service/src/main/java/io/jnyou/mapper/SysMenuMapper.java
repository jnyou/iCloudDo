package io.jnyou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.jnyou.domain.SysMenu;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    /**
     * 通过用户id查询菜单数据
     * @param userId
     */
    List<SysMenu> selectMenusByUserId(Long userId);
}
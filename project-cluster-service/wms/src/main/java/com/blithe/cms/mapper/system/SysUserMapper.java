package com.blithe.cms.mapper.system;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.blithe.cms.pojo.system.SysUser;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 夏小颜
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 据用户id查询所有拥有的角色
     * @param uid
     * @return
     */
    List<Integer> selectRidByUid(Integer uid);

    /**
     * 批量保存用户id和角色id
     * @param params
     * @return
     */
    void insertBatchUidAndRid(List<Map<String, Object>> params);

    /**
     * 据用户uid删除所有的角色信息
     * @param uid
     */
    void deleteByUid(Serializable uid);
}
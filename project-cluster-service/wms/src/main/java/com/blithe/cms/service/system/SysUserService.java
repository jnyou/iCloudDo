package com.blithe.cms.service.system;


import com.baomidou.mybatisplus.service.IService;
import com.blithe.cms.pojo.system.SysUser;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * (SysUser)表服务接口
 *
 * @author makejava
 * @since 2020-03-19 09:53:11
 */
public interface SysUserService extends IService<SysUser> {

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
     * 删除用户及中间表的角色id
     * @param id
     * @return
     */
    @Override
    boolean deleteById(Serializable id);
}
package com.blithe.cms.service.system.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.mapper.system.SysUserMapper;
import com.blithe.cms.pojo.system.SysUser;
import com.blithe.cms.service.system.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * (SysUser)表服务实现类
 *
 * @author makejava
 * @since 2020-03-19 09:55:47
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public List<Integer> selectRidByUid(Integer uid) {
        return userMapper.selectRidByUid(uid);
    }


    @Override
    public void insertBatchUidAndRid(List<Map<String, Object>> params) {
        //根据uid删除sys_role_user所有的rid
        if(CollectionUtils.isNotEmpty(params) && params.size() > 0){
            userMapper.deleteByUid((Serializable) params.get(0).get("uid"));
            userMapper.insertBatchUidAndRid(params);
        }

    }

    @Override
    public boolean deleteById(Serializable id) {
        if(null != id){
            userMapper.deleteByUid(id);
        }
        return super.deleteById(id);
    }
}
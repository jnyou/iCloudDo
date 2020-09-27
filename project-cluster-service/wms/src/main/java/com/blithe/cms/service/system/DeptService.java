package com.blithe.cms.service.system;


import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.blithe.cms.common.tools.DataGridView;
import com.blithe.cms.pojo.system.Dept;

import java.io.Serializable;

/**
 * 部门管理
 * @author 夏小颜
 */
public interface DeptService extends IService<Dept> {

    /**
     * 查询
     * @param id
     * @return
     */
    @Override
    Dept selectById(Serializable id);

    /**
     * 更新
     * @param dept
     * @return
     */
    Dept updateDeptById(Dept dept);

    /**
     * 保存
     * @param dept
     * @return
     */
    Dept saveDept(Dept dept);


    /**
     * 部门树
     * @param
     * @return
     */
    DataGridView loadDeptLeftDtreeJson();

}

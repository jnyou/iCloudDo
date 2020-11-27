package com.blithe.cms.service.system.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.blithe.cms.common.utils.DataGridView;
import com.blithe.cms.common.utils.TreeNode;
import com.blithe.cms.config.redis.RedisCompoent;
import com.blithe.cms.mapper.system.DeptMapper;
import com.blithe.cms.pojo.system.Dept;
import com.blithe.cms.service.system.DeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 部门管理
 * @author 夏小颜
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

	@Autowired
	private DeptMapper deptMapper;

	@Autowired
	private RedisCompoent redisCompoent;

	@Override
	@Cacheable(value = {"dept"},key = "#p0",sync=true)
	public Dept selectById(Serializable id) {
		return deptMapper.selectById(id);
	}

	/**
	 *  延时双删策略
	 *         在写库前后都进行redis.del(key)操作，并且设定合理的超时时间。具体步骤是：
	 *
	 *         1）先删除缓存
	 *
	 *         2）再写数据库
	 *
	 *         3）休眠500毫秒（根据具体的业务时间来定）
	 *
	 *         4）再次删除缓存。
	 *
	 * @param dept
	 * @return
	 */
	@Override
	@CachePut(value = {"dept"},key = "#p0.id")
	public Dept updateDeptById(Dept entity) {
//		redisCompoent.remove("updateById_" + dept.getId());
//		boolean editFlag = super.updateById(dept);
//		try {
//			Thread.sleep( 500 );
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		redisCompoent.remove("updateById_" + dept.getId());
		Dept dept = new Dept();
		super.updateById(entity);
		BeanUtils.copyProperties(entity,dept);
		return dept;
	}

	@Override
	@CacheEvict(value = {"dept"},allEntries = true)
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}

	@Override
	@CachePut(cacheNames = "dept",key = "#result.id")
	public Dept saveDept(Dept entity) {
		Dept dept = new Dept();
		deptMapper.insert(entity);
		BeanUtils.copyProperties(entity,dept);
		return dept;
	}

	/**
	 * 部门菜单
	 */
	@Override
	public DataGridView loadDeptLeftDtreeJson(){

		List<Dept> deptList = this.deptMapper.selectList(new EntityWrapper<>());
		List<TreeNode> treeNodes = new ArrayList<>();
		for (Dept dept : deptList) {
			Boolean openFlag = dept.getOpen() == 1 ? true: false;
			treeNodes.add(new TreeNode(dept.getId(),dept.getPid(),dept.getTitle(),openFlag));
		}
		return new DataGridView(treeNodes);
	}

}

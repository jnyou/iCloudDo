package com.blithe.cms.controller.system;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.blithe.cms.common.exception.R;
import com.blithe.cms.common.tools.*;
import com.blithe.cms.pojo.system.Permission;
import com.blithe.cms.service.system.PermissionService;
import com.blithe.cms.service.system.RoleService;
import com.blithe.cms.service.system.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: youjiannan
 * @Description: menu
 * @Date: 2020/3/19
 * @Param:
 * @Return:
 **/
@RestController
@RequestMapping("/menu")
public class MenuController {


	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SysUserService userService;

    /**
     * load index left dtree meun
     * @param permission
     * @return
     */
	@GetMapping("/loadIndexLeftMenuJson")
	public DataGridView loadIndexLeftMenuJson() {
		return permissionService.loadIndexLeftMenuJson();
	}

/*****************************************menu manager start***********************************************/
	/**
     * load menu manager left dtree  JSON data
	 * @return
	 */
	@PostMapping("/loadMenuLeftDtreeJson")
	public DataGridView loadMenuLeftDtreeJson() {
		//设置只能查询菜单
		List<Permission> permissionList = this.permissionService.selectList(new EntityWrapper().eq("type", Constast.TYPE_MNEU));
		List<TreeNode> treeNodes = new ArrayList<>();
		for (Permission Permission : permissionList) {
			Boolean openFlag = Permission.getOpen() == 1 ? true: false;
			treeNodes.add(new TreeNode(Permission.getId(),Permission.getPid(),Permission.getTitle(),openFlag));
		}
		return new DataGridView(treeNodes);
	}


	/**
	 * query menu
	 * @param Permission
	 * @param params
	 * @return
	 */
	@GetMapping("/list")
	public R queryMenuList(Permission permission){
		Page page = new Page<>(permission.getPage(),permission.getLimit(),"ordernum",true);
		Wrapper wrapper = new EntityWrapper();
		wrapper.like(StringUtils.isNotBlank(permission.getTitle()),"title",permission.getTitle());
        wrapper.eq("type", Constast.TYPE_MNEU);
		// 接受前台left参数进行渲染数据表格
        if(permission.getId()!=null){
            wrapper.eq("id",permission.getId()).or().eq("pid",permission.getId());
        }
		this.permissionService.selectPage(page, wrapper);

		return R.ok().put("count",page.getTotal()).put("data",page.getRecords());

	}


	@PostMapping("/checkMenuChildrenDel")
	public R checkMenuChildrenDel(@RequestBody String id){
		try {
			EntityWrapper wrapper = new EntityWrapper();
            wrapper.eq("type", Constast.TYPE_MNEU);
			wrapper.eq("pid",id);
			List list = this.permissionService.selectList(wrapper);
			if(CollectionUtils.isNotEmpty(list)){
				return R.ok().put("value",true);
			}else {
                permissionService.deleteById(Integer.parseInt(id));
			}
        }catch (Exception e){
            return R.error(e.getMessage());
        }
        return R.ok();
    }

	/**
	 * save
	 * @param Permission
	 * @return
	 */
	@PostMapping("/menuSaveOrUpdate")
	public R menuSaveOrUpdate(Permission Permission){
		try {
			if(Permission.getId() == null){
				Permission.setType(Constast.TYPE_MNEU);
				this.permissionService.insert(Permission);
			}else{
				this.permissionService.updateById(Permission);
			}
		}catch (Exception e){
			return R.error(e.getMessage());
		}
		return R.ok();
	}

	/**
     * query max ordernum
	 * @return
	 */
	@GetMapping("/loadMenuMaxOrderNum")
	public R loadMenuMaxOrderNum(){
		EntityWrapper wrapper = new EntityWrapper();
		wrapper.eq("type", Constast.TYPE_MNEU);
		wrapper.orderBy("ordernum",false).last("LIMIT 1");
		Permission permission = this.permissionService.selectOne(wrapper);
		if(permission != null && permission.getOrdernum()>0){
			return R.ok().put("error",true).put("value",permission.getOrdernum() + 1);
		}else{
			return R.ok().put("error",false);
		}
	}

/*****************************************menu manager end***********************************************/

}


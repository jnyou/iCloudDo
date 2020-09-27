package com.blithe.cms.common.tools;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 夏小颜
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
	
	private Integer id;
	@JsonProperty("parentId")
	private Integer pid;
	private String title;
	private String icon;
	private String href;
	private Boolean spread;
	private String checkArr = "0";
	private List<TreeNode> children=new ArrayList<TreeNode>();


	/**
	 * 部门管理dtree
	 * @param id
	 * @param pid
	 * @param title
	 * @param spread
	 */
	public TreeNode(Integer id, Integer pid, String title, Boolean spread) {
		this.id = id;
		this.pid = pid;
		this.title = title;
		this.spread = spread;
	}

	/**
	 *首页左边导航树的构造器
	 */
	public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread) {
		super();
		this.id = id;
		this.pid = pid;
		this.title = title;
		this.icon = icon;
		this.href = href;
		this.spread = spread;
	}

	/**
	 * 权限分配的树
	 * @param id
	 * @param pid
	 * @param title
	 * @param checkArr
	 */
	public TreeNode(Integer id, Integer pid, String title,Boolean spread, String checkArr) {
		this.id = id;
		this.pid = pid;
		this.title = title;
		this.spread = spread;
		this.checkArr = checkArr;
	}
}

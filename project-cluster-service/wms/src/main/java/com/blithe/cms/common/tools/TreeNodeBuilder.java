package com.blithe.cms.common.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 夏小颜
 */
public class TreeNodeBuilder {
	

	/**
	 * 把没有层级关系的集合变成有层级关系的
	 * @param treeNodes
	 * @param topId
	 * @return
	 */
	public static List<TreeNode> build(List<TreeNode> treeNodes,Integer topPid){
		List<TreeNode> nodes=new ArrayList<>();
		for (TreeNode n1 : treeNodes) {
			if(!StringUtil.isNull(n1.getPid())){
				if(n1.getPid().equals(topPid)) {
					nodes.add(n1);
				}
				for (TreeNode n2 : treeNodes) {
					if(n1.getId().equals(n2.getPid())) {
						n1.getChildren().add(n2);
					}
				}
			}
		}
		return nodes;
	}
}

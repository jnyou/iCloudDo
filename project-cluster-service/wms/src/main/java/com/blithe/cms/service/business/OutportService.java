package com.blithe.cms.service.business;


import com.baomidou.mybatisplus.service.IService;
import com.blithe.cms.pojo.business.Outport;

/**
 * @Author: youjiannan
 * @Description:
 * @Date: 2020/4/3
 * @Param:
 * @Return:
 **/
public interface OutportService extends IService<Outport> {

	/**
	 * 退货
	 * @param id  进货单ID
	 * @param number  退货数量
	 * @param remark  备注
	 */
	void addOutPort(Integer id, Integer number, String remark);

}

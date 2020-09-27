package com.blithe.cms.service.business;

import com.baomidou.mybatisplus.service.IService;
import com.blithe.cms.pojo.business.Salesback;
/**
 * @author 夏小颜
 */
public interface SalesbackService extends IService<Salesback> {

    /**
     * 销售退货
     * @param id  销售id
     * @param number  销售数量
     * @param remark  销售备注
     */
    void salesConfirm(Integer id, Integer number, String remark);
}

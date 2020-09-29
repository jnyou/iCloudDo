package org.jnyou.gmall.storageservice.dao;

import org.jnyou.gmall.storageservice.entity.PurchaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购信息
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {
	
}

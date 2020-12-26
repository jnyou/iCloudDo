package org.jnyou.gmall.couponservice.dao;

import org.jnyou.gmall.couponservice.entity.SeckillSessionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀活动场次
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface SeckillSessionDao extends BaseMapper<SeckillSessionEntity> {
	
}

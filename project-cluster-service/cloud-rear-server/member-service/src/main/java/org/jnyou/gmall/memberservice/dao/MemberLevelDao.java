package org.jnyou.gmall.memberservice.dao;

import org.jnyou.gmall.memberservice.entity.MemberLevelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员等级
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface MemberLevelDao extends BaseMapper<MemberLevelEntity> {
	
}

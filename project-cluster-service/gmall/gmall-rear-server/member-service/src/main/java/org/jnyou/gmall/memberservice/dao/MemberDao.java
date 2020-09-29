package org.jnyou.gmall.memberservice.dao;

import org.jnyou.gmall.memberservice.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}

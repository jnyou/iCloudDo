package org.jnyou.gmall.productservice.dao;

import org.jnyou.gmall.productservice.entity.CommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 * 
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 * @date 2020-09-24 15:45:06
 */
@Mapper
public interface CommentReplayDao extends BaseMapper<CommentReplayEntity> {
	
}

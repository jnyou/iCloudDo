package org.jnyou.gmall.memberservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.memberservice.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


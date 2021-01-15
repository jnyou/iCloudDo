package org.jnyou.gmall.memberservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.memberservice.entity.MemberEntity;
import org.jnyou.gmall.memberservice.exception.PhoneExistException;
import org.jnyou.gmall.memberservice.exception.UsernameExistException;
import org.jnyou.gmall.memberservice.vo.MemberLoginVo;
import org.jnyou.gmall.memberservice.vo.MemberRegistVo;

import java.util.Map;

/**
 * 会员
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(MemberRegistVo vo);

    void checkPhoneUnique(String email) throws PhoneExistException;

    void checkUserNameUnique(String username) throws UsernameExistException;

    MemberEntity login(MemberLoginVo vo);
}


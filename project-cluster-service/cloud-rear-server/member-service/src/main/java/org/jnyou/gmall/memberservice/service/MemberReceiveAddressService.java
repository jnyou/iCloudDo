package org.jnyou.gmall.memberservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.gmall.memberservice.entity.MemberReceiveAddressEntity;

import java.util.List;
import java.util.Map;

/**
 * 会员收货地址
 *
 * @author jnyou
 * @email xiaojian19970910@gmail.com
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MemberReceiveAddressEntity> getAddresses(Long memberId);
}


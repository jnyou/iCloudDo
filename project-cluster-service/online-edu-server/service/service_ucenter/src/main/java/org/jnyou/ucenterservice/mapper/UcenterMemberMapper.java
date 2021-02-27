package org.jnyou.ucenterservice.mapper;

import org.jnyou.commonutils.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author jnyou
 * @since 2020-06-26
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 统计某天的注册人数
     * @param date
     * @return
     * @Author jnyou
     * @Date 2020/8/22
     */
    Integer selectRegisterCount(String date);
}

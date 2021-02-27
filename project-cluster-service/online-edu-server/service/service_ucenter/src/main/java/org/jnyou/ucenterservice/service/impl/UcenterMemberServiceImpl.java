package org.jnyou.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jnyou.commonutils.JwtUtils;
import org.jnyou.commonutils.MD5;
import org.jnyou.servicebase.exception.IsMeException;
import org.jnyou.commonutils.entity.UcenterMember;
import org.jnyou.ucenterservice.entity.vo.RegisterVo;
import org.jnyou.ucenterservice.mapper.UcenterMemberMapper;
import org.jnyou.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author jnyou
 * @since 2020-06-26
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private UcenterMemberMapper memberMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 登陆返回token
     * @param member
     * @return
     */
    @Override
    public String login(UcenterMember member) {
        // 获取登陆手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //校验参数
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new IsMeException(-1,"登陆失败");
        }
        // 根据手机号查询用户信息
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = memberMapper.selectOne(wrapper);
        // 判断对象是否位空
        if(null == ucenterMember) {
            throw new IsMeException(-1,"用户不存在");
        }
        //校验密码
        if(!MD5.encrypt(password).equals(ucenterMember.getPassword())) {
            throw new IsMeException(-1,"用户名或密码不正确");
        }
        //校验是否被禁用
        if(ucenterMember.getIsDisabled()) {
            throw new IsMeException(-1,"用户被禁用，请联系管理员");
        }
        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return token;
    }

    /**
     * 注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(nickname) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new IsMeException(-1,"必填项不能为空");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobileCode = redisTemplate.opsForValue().get("phone");
        if(!code.equals(mobileCode)) {
            throw new IsMeException(-1,"验证码不正确");
        }
        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(count.intValue() > 0) {
            throw new IsMeException(-1,"该手机已经注册，请直接登陆");
        }
        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://jnyou.oss-cn-beijing.aliyuncs.com/u%3D2529138109%2C771074326%26fm%3D26%26gp%3D0.jpg");
        memberMapper.insert(member);

    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);

        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public Integer selectRegisterCount(String date) {
        return baseMapper.selectRegisterCount(date);
    }
}

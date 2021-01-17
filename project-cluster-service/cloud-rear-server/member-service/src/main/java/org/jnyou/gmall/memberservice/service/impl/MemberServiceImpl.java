package org.jnyou.gmall.memberservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jnyou.common.utils.HttpUtils;
import org.jnyou.gmall.memberservice.entity.MemberLevelEntity;
import org.jnyou.gmall.memberservice.exception.PhoneExistException;
import org.jnyou.gmall.memberservice.exception.UsernameExistException;
import org.jnyou.gmall.memberservice.service.MemberLevelService;
import org.jnyou.gmall.memberservice.vo.MemberLoginVo;
import org.jnyou.gmall.memberservice.vo.MemberRegistVo;
import org.jnyou.gmall.memberservice.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jnyou.common.utils.PageUtils;
import org.jnyou.common.utils.Query;

import org.jnyou.gmall.memberservice.dao.MemberDao;
import org.jnyou.gmall.memberservice.entity.MemberEntity;
import org.jnyou.gmall.memberservice.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberLevelService memberLevelService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void regist(MemberRegistVo vo) {
        MemberEntity memberEntity = new MemberEntity();
        // 设置注册时的默认等级
        MemberLevelEntity memberLevelEntity = memberLevelService.getDefaultLevel();
        memberEntity.setLevelId(memberLevelEntity.getId());

        // 检查用户名和手机号是否唯一 使用异常机制让远程感知异常
        checkPhoneUnique(vo.getPhone());
        checkUserNameUnique(vo.getUserName());
        memberEntity.setMobile(vo.getPhone());
        memberEntity.setUsername(vo.getUserName());

        // 密码需要加密存储 使用spring的密码加密器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        memberEntity.setPassword(encode);
        this.baseMapper.insert(memberEntity);
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneExistException {
        Integer count = this.baseMapper.selectCount(Wrappers.<MemberEntity>lambdaQuery().eq(MemberEntity::getMobile, phone));
        if(count > 0){
            throw new PhoneExistException();
        }
    }

    @Override
    public void checkUserNameUnique(String username) throws UsernameExistException {
        Integer count = this.baseMapper.selectCount(Wrappers.<MemberEntity>lambdaQuery().eq(MemberEntity::getUsername, username));
        if(count > 0){
            throw new UsernameExistException();
        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        MemberEntity memberEntity = this.baseMapper.selectOne(Wrappers.<MemberEntity>lambdaQuery()
                .eq(MemberEntity::getUsername, vo.getLoginAccount())
                .or()
                .eq(MemberEntity::getMobile, vo.getLoginAccount())
        );
        if(null == memberEntity){
            // 登录失败
            return null;
        } else {
            // 获取到数据库的password
            String passwordDb = memberEntity.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // 第一个传递过来的明文密码，第二个是数据库存储的密文密码进行匹配
            boolean matches = passwordEncoder.matches(vo.getPassword(), passwordDb);
            if(matches){
                return memberEntity;
            } else {
                return null;
            }
        }
    }

    /**
     * 社交登录
     * @param user
     * @Author JnYou
     */
    @Override
    public MemberEntity oauthLogin(SocialUser socialUser) {
        // 登录和注册合并逻辑
        String uid = socialUser.getUid();
        MemberEntity memberEntity = this.getOne(new QueryWrapper<MemberEntity>().eq("uid", uid));
        if (memberEntity == null) {
            //1 如果之前未登陆过，则查询其社交信息进行注册
            Map<String, String> query = new HashMap<>();
            query.put("access_token",socialUser.getAccess_token());
            query.put("uid", uid);
            //封装用户信息并保存
            memberEntity = new MemberEntity();
            try {
                //调用微博api接口获取用户信息
                HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<>(), query);
                String json = EntityUtils.toString(response.getEntity());
                JSONObject jsonObject = JSON.parseObject(json);
                //获得昵称，性别，头像
                String name = jsonObject.getString("name");
                String gender = jsonObject.getString("gender");
                String profile_image_url = jsonObject.getString("profile_image_url");
                memberEntity.setNickname(name);
                memberEntity.setGender("m".equals(gender)?0:1);
                memberEntity.setHeader(profile_image_url);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("invoke weibo api fail.");
            }
            // 查找用户的默认级别
            MemberLevelEntity defaultLevel = memberLevelService.getOne(new QueryWrapper<MemberLevelEntity>().eq("default_status", 1));
            memberEntity.setLevelId(defaultLevel.getId());
            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setUid(socialUser.getUid());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            this.save(memberEntity);
        }else {
            //2 否则更新令牌等信息并返回
            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setUid(socialUser.getUid());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            this.updateById(memberEntity);
        }
        return memberEntity;
    }

}
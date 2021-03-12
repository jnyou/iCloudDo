package io.jnyou.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.jnyou.config.IDGenConfig;
import io.jnyou.config.IdAutoConfiguration;
import io.jnyou.domain.Sms;
import io.jnyou.domain.User;
import io.jnyou.domain.UserAuthAuditRecord;
import io.jnyou.domain.UserAuthInfo;
import io.jnyou.dto.UserDto;
import io.jnyou.geetest.GeetestLib;
import io.jnyou.mapper.UserMapper;
import io.jnyou.mappers.UserDtoMapper;
import io.jnyou.model.RegisterParam;
import io.jnyou.model.UpdatePhoneParam;
import io.jnyou.model.UserAuthForm;
import io.jnyou.service.UserAuthAuditRecordService;
import io.jnyou.service.UserAuthInfoService;
import io.jnyou.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserAuthAuditRecordService userAuthAuditRecordService ;

    @Autowired
    GeetestLib geetestLib;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    UserAuthInfoService userAuthInfoService;
    @Autowired
    Snowflake snowflake;
    @Autowired
    StringRedisTemplate stringRedisTemplate;


    /**
     * 条件分页查询会员的列表
     *
     * @param page     分页参数
     * @param mobile   会员的手机号
     * @param userId   会员的ID
     * @param userName 会员的名称
     * @param realName 会员的真实名称
     * @param status   会员的状态
     * @return
     */
    @Override
    public Page<User> findByPage(Page<User> page, String mobile, Long userId, String userName, String realName, Integer status, Integer reviewStatus) {
        return page(page,
                new LambdaQueryWrapper<User>()
                        .like(!StringUtils.isEmpty(mobile), User::getMobile, mobile)
                        .like(!StringUtils.isEmpty(userName), User::getUsername, userName)
                        .like(!StringUtils.isEmpty(realName), User::getRealName, realName)
                        .eq(userId != null, User::getId, userId)
                        .eq(status != null, User::getStatus, status)
                        .eq(reviewStatus != null, User::getReviewsStatus, reviewStatus)
        );
    }

    /**
     * 通过用户的Id 查询该用户邀请的人员
     *
     * @param page   分页参数
     * @param userId 用户的Id
     * @return
     */
    @Override
    public Page<User> findDirectInvitePage(Page<User> page, Long userId) {
        return page(page, new LambdaQueryWrapper<User>().eq(User::getDirectInviteid, userId));
    }

    /**
     * 修改用户的审核状态
     *
     * @param id
     * @param authStatus
     * @param authCode
     */
    @Override
    @Transactional
    public void updateUserAuthStatus(Long id, Byte authStatus, Long authCode,String remark) {
        log.info("开始修改用户的审核状态,当前用户{},用户的审核状态{},图片的唯一code{}",id,authStatus,authCode);
        User user = getById(id);
        if(user!=null){
//            user.setAuthStatus(authStatus); // 认证的状态
            user.setReviewsStatus(authStatus.intValue()); // 审核的状态
            updateById(user) ; // 修改用户的状态
        }
        UserAuthAuditRecord userAuthAuditRecord = new UserAuthAuditRecord();
        userAuthAuditRecord.setUserId(id);
        userAuthAuditRecord.setStatus(authStatus);
        userAuthAuditRecord.setAuthCode(authCode);

        String usrStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString() ;
        userAuthAuditRecord.setAuditUserId(Long.valueOf(usrStr)); // 审核人的ID
        userAuthAuditRecord.setAuditUserName("---------------------------"); // 审核人的名称 --> 远程调用admin-service ,没有事务
        userAuthAuditRecord.setRemark(remark);

        userAuthAuditRecordService.save(userAuthAuditRecord) ;
    }


    /**
     * 用户的实名认证
     *
     * @param id           用户的Id
     * @param userAuthForm 认证的表单数据
     * @return 认证的结果
     */
    @Override
    public boolean identifyVerify(Long id, UserAuthForm userAuthForm) {
        User user = getById(id);
        Assert.notNull(user, "认证的用户不存在");
        Byte authStatus = user.getAuthStatus();
        if (!authStatus.equals((byte) 0)) {
            throw new IllegalArgumentException("该用户已经认证成功了");
        }
        // 执行认证
        checkForm(userAuthForm); // 极验
        // 实名认证
        boolean check = IdAutoConfiguration.check(userAuthForm.getRealName(), userAuthForm.getIdCard());
        if (!check) {
            throw new IllegalArgumentException("该用户信息错误,请检查");
        }

        // 设置用户的认证属性
        user.setAuthtime(new Date());
        user.setAuthStatus((byte) 1);
        user.setRealName(userAuthForm.getRealName());
        user.setIdCard(userAuthForm.getIdCard());
        user.setIdCardType(userAuthForm.getIdCardType());

        return updateById(user);
    }


    private void checkForm(UserAuthForm userAuthForm) {
        userAuthForm.check(geetestLib, redisTemplate);
    }

    @Override
    public User getById(Serializable id) {
        User user = super.getById(id);
        if(user==null){
            throw new IllegalArgumentException("请输入正确的用户Id") ;
        }
        Byte seniorAuthStatus = null ; // 用户的高级认证状态
        String seniorAuthDesc = "" ;// 用户的高级认证未通过,原因
        Integer reviewsStatus = user.getReviewsStatus(); // 用户被审核的状态 1通过,2拒绝,0,待审核"
        if(reviewsStatus == null){ //为null 时,代表用户的资料没有上传
            seniorAuthStatus = 3 ;
            seniorAuthDesc = "资料未填写" ;
        }else {
            switch (reviewsStatus) {
                case 1:
                    seniorAuthStatus = 1;
                    seniorAuthDesc = "审核通过";
                    break;
                case 2:
                    seniorAuthStatus = 2;
                    // 查询被拒绝的原因--->审核记录里面的
                    // 时间排序,
                    List<UserAuthAuditRecord> userAuthAuditRecordList = userAuthAuditRecordService.getUserAuthAuditRecordList(user.getId());
                    if (!CollectionUtils.isEmpty(userAuthAuditRecordList)) {
                        UserAuthAuditRecord userAuthAuditRecord = userAuthAuditRecordList.get(0);
                        seniorAuthDesc = userAuthAuditRecord.getRemark();
                    }
//                    seniorAuthDesc = "原因未知";
                    break;
                case 0:
                    seniorAuthStatus = 0;
                    seniorAuthDesc = "等待审核";
                    break;

            }
        }
        user.setSeniorAuthStatus(seniorAuthStatus);
        user.setSeniorAuthDesc(seniorAuthDesc);
        return user;
    }

    /**
     * 用户的高级认证
     *
     * @param id   用户的Id
     * @param imgs
     */
    @Override
    @Transactional
    public void authUser(Long id, List<String> imgs) {
        if (CollectionUtils.isEmpty(imgs)) {
            throw new IllegalArgumentException("用户的身份证信息为null");
        }
        User user = getById(id);
        if (user == null) {
            throw new IllegalArgumentException("请输入正确的userId");
        }
        long authCode = snowflake.nextId(); // 使用时间戳(有重复) --> 雪花算法
        List<UserAuthInfo> userAuthInfoList = new ArrayList<>(imgs.size());
        for (int i = 0; i < imgs.size(); i++) { // 有序排列
            String s = imgs.get(i);
            UserAuthInfo userAuthInfo = new UserAuthInfo();
            userAuthInfo.setImageUrl(imgs.get(i));
            userAuthInfo.setUserId(id);
            userAuthInfo.setSerialno(i + 1);  // 设置序号 ,1 正面  2 反面 3 手持
            userAuthInfo.setAuthCode(authCode); // 是一组身份信息的标识 3 个图片为一组
            userAuthInfoList.add(userAuthInfo);
        }
        userAuthInfoService.saveBatch(userAuthInfoList); // 批量操作

        user.setReviewsStatus(0); // 等待审核
        updateById(user); // 更新用户的状态

    }

    /**
     * 修改用户的手机号号
     *
     * @param updatePhoneParam
     * @return
     */
    @Override
    public boolean updatePhone(Long userId , UpdatePhoneParam updatePhoneParam) {
        // 1 使用 userId 查询用户
        User user = getById(userId);

        // 2 验证旧手机
        String oldMobile = user.getMobile(); // 旧的手机号 --- > 验证旧手机的验证码
        String oldMobileCode = stringRedisTemplate.opsForValue().get("SMS:VERIFY_OLD_PHONE:" + oldMobile);
        if(!updatePhoneParam.getOldValidateCode().equals(oldMobileCode)){
            throw new IllegalArgumentException("旧手机的验证码错误") ;
        }

        // 3 验证新手机
        String newPhoneCode = stringRedisTemplate.opsForValue().get("SMS:CHANGE_PHONE_VERIFY:" + updatePhoneParam.getNewMobilePhone());
        if(!updatePhoneParam.getValidateCode().equals(newPhoneCode)){
            throw new IllegalArgumentException("新手机的验证码错误") ;
        }

        // 4 修改手机号
        user.setMobile(updatePhoneParam.getNewMobilePhone());

        return updateById(user);
    }


    /**
     * 检验新的手机号是否可用,若可用,则给新的手机号发送一个验证码
     *
     * @param mobile      新的手机号
     * @param countryCode 国家代码
     * @return
     */
    @Override
    public boolean checkNewPhone(String mobile, String countryCode) {
        //1 新的手机号,没有旧的用户使用
        int count = count(new LambdaQueryWrapper<User>().eq(User::getMobile, mobile).eq(User::getCountryCode,countryCode));
        if(count>0){ // 有用户占用这个手机号
            throw new IllegalArgumentException("该手机号已经被占用") ;
        }
        // 2 向新的手机发送短信
        Sms sms = new Sms();
        sms.setMobile(mobile);
        sms.setCountryCode(countryCode);
        sms.setTemplateCode("CHANGE_PHONE_VERIFY"); // 模板代码  -- > 校验手机号
//        smsService.sendSms(sms); 远程发送验证码
        return Boolean.TRUE;
    }

    /**
     * 查询用户的基本信息
     *
     * @param ids 用户的Id
     * @return
     */
    @Override
    public List<UserDto> getBasicUsers(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return Collections.emptyList() ;
        }
        List<User> list = list(new LambdaQueryWrapper<User>().in(User::getId, ids));
        if(CollectionUtils.isEmpty(list)) return Collections.emptyList();
        // 对象的转化
        List<UserDto> userDtos = UserDtoMapper.INSTANCE.convert2Dto(list);
        return userDtos;
    }

    /**
     * 用户的注册
     *
     * @param registerParam 注册的表单参数
     * @return
     */
    @Override
    public boolean register(RegisterParam registerParam) {
        log.info("用户开始注册{}", JSON.toJSONString(registerParam, true));
        String mobile = registerParam.getMobile();
        String email = registerParam.getEmail();
        // 1 简单的校验
        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)) {
            throw new IllegalArgumentException("手机号或邮箱不能同时为空");
        }
        // 2 查询校验
        int count = count(new LambdaQueryWrapper<User>()
                .eq(!StringUtils.isEmpty(email), User::getEmail, email)
                .eq(!StringUtils.isEmpty(mobile), User::getMobile, mobile)
        );
        if(count>0){
            throw new IllegalArgumentException("手机号或邮箱已经被注册");
        }

        registerParam.check(geetestLib, redisTemplate); // 进行极验的校验
        User user = getUser(registerParam); // 构建一个新的用户
        return save(user);
    }

    private User getUser(RegisterParam registerParam) {
        User user = new User();
        user.setCountryCode(registerParam.getCountryCode());
        user.setEmail(registerParam.getEmail());
        user.setMobile(registerParam.getMobile());
        String encodePwd = new BCryptPasswordEncoder().encode(registerParam.getPassword());
        user.setPassword(encodePwd);
        user.setPaypassSetting(false);
        user.setStatus((byte) 1);
        user.setType((byte) 1);
        user.setAuthStatus((byte) 0);
        user.setLogins(0);
        user.setInviteCode(RandomUtil.randomString(6)); // 用户的邀请码
        if (!StringUtils.isEmpty(registerParam.getInvitionCode())) {
            User userPre = getOne(new LambdaQueryWrapper<User>().eq(User::getInviteCode, registerParam.getInvitionCode()));
            if (userPre != null) {
                user.setDirectInviteid(String.valueOf(userPre.getId())); // 邀请人的id , 需要查询
                user.setInviteRelation(String.valueOf(userPre.getId())); // 邀请人的id , 需要查询
            }

        }
        return user;
    }

}

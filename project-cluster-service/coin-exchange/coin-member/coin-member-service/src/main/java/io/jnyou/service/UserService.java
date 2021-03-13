package io.jnyou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jnyou.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jnyou.dto.UserDto;
import io.jnyou.model.RegisterParam;
import io.jnyou.model.UpdatePhoneParam;
import io.jnyou.model.UserAuthForm;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User>{

    /**
     * 条件分页查询会员的列表
     * @param page
     * 分页参数
     * @param mobile
     * 会员的手机号
     * @param userId
     * 会员的ID
     * @param userName
     * 会员的名称
     * @param realName
     * 会员的真实名称
     * @param status
     * 会员的状态
     * @param reviewStatus
     * 会员的审核状态
     * @return
     */
    Page<User> findByPage(Page<User> page, String mobile, Long userId, String userName, String realName, Integer status ,Integer reviewStatus);

    /**
     * 通过用户的Id 查询该用户邀请的人员
     * @param page
     * 分页参数
     * @param userId
     * 用户的Id
     * @return
     */
    Page<User> findDirectInvitePage(Page<User> page, Long userId);

    /**
     * 修改用户的审核状态
     * @param id
     * @param authStatus
     * @param authCode
     * @param remark 拒绝的原因
     *
     *
     */
    void updateUserAuthStatus(Long id, Byte authStatus, Long authCode,String remark);

    /**
     * 用户的实名认证
     * @param id 用户的Id
     * @param userAuthForm 认证的结果
     * 认证的表单数据
     * @return
     *
     */
    boolean identifyVerify(Long valueOf, UserAuthForm userAuthForm);

    /**
     * 用户的高级认证
     * @param id
     *  用户的Id
     *
     * @param imgs
     *  用户的图片地址
     */
    void authUser(Long id, List<String> imgs);

    /**
     * 修改用户的手机号号
     * @param userId
     * @param updatePhoneParam
     * @return
     */
    boolean updatePhone(Long userId ,UpdatePhoneParam updatePhoneParam);

    /**
     * 检验新的手机号是否可用,若可用,则给新的手机号发送一个验证码
     * @param mobile
     * 新的手机号
     * @param countryCode
     * 国家代码
     * @return
     */
    boolean checkNewPhone(String mobile, String countryCode);

    /**
     * 通过用户的id集合查询用户信息
     * @param ids
     * @param userName
     * @param mobile
     * @return
     */
    Map<Long, UserDto> getBasicUsers(List<Long> ids, String userName, String mobile);

    /**
     * 用户的注册
     * @param registerParam
     * 注册的表单参数
     * @return
     */
    boolean register(RegisterParam registerParam);

}

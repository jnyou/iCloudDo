package io.jnyou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jnyou.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
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
}

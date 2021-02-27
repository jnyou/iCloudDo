package org.jnyou.aclservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jnyou.aclservice.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author jnyou
 */
public interface UserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);
}

package io.jnyou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jnyou.domain.UserWallet;
import com.baomidou.mybatisplus.extension.service.IService;
public interface UserWalletService extends IService<UserWallet>{


    /**
     * 分页查询用户的提币地址
     * @param page
     * 分页参数
     * @param userId
     * 用户的ID
     * @return
     */
    Page<UserWallet> findByPage(Page<UserWallet> page, Long userId);
}

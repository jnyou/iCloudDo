package io.jnyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.UserAddress;
import io.jnyou.mapper.UserAddressMapper;
import io.jnyou.service.UserAddressService;
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService{

    /**
     * 通过用户的Id 分页查询用户的钱包地址
     *
     * @param page   分页参数
     * @param userId 用户的Id
     * @return
     */
    @Override
    public Page<UserAddress> findByPage(Page<UserAddress> page, Long userId) {
        return page(page ,new LambdaQueryWrapper<UserAddress>().eq(UserAddress::getUserId,userId));
    }

}

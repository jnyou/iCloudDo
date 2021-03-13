package io.jnyou.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.UserAccountFreeze;
import io.jnyou.mapper.UserAccountFreezeMapper;
import io.jnyou.service.UserAccountFreezeService;
import org.springframework.stereotype.Service;

@Service
public class UserAccountFreezeServiceImpl extends ServiceImpl<UserAccountFreezeMapper, UserAccountFreeze> implements UserAccountFreezeService {

}


package io.jnyou.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.UserCoinFreeze;
import io.jnyou.mapper.UserCoinFreezeMapper;
import io.jnyou.service.UserCoinFreezeService;
import org.springframework.stereotype.Service;

@Service
public class UserCoinFreezeServiceImpl extends ServiceImpl<UserCoinFreezeMapper, UserCoinFreeze> implements UserCoinFreezeService {

}


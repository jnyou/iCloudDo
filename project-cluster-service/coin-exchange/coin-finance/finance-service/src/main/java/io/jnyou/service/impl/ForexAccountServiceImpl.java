package io.jnyou.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.ForexAccount;
import io.jnyou.mapper.ForexAccountMapper;
import io.jnyou.service.ForexAccountService;
import org.springframework.stereotype.Service;

@Service
public class ForexAccountServiceImpl extends ServiceImpl<ForexAccountMapper, ForexAccount> implements ForexAccountService {

}


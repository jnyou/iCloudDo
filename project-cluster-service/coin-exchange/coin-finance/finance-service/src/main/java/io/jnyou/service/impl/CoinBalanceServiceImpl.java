package io.jnyou.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.CoinBalance;
import io.jnyou.mapper.CoinBalanceMapper;
import io.jnyou.service.CoinBalanceService;
import org.springframework.stereotype.Service;

@Service
public class CoinBalanceServiceImpl extends ServiceImpl<CoinBalanceMapper, CoinBalance> implements CoinBalanceService {

}


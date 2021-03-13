package io.jnyou.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.ForexCoin;
import io.jnyou.mapper.ForexCoinMapper;
import io.jnyou.service.ForexCoinService;
import org.springframework.stereotype.Service;

@Service
public class ForexCoinServiceImpl extends ServiceImpl<ForexCoinMapper, ForexCoin> implements ForexCoinService {

}


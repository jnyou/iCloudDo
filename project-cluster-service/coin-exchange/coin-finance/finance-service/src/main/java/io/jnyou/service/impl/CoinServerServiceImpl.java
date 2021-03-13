package io.jnyou.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.CoinServer;
import io.jnyou.mapper.CoinServerMapper;
import io.jnyou.service.CoinServerService;
import org.springframework.stereotype.Service;

@Service
public class CoinServerServiceImpl extends ServiceImpl<CoinServerMapper, CoinServer> implements CoinServerService {

}


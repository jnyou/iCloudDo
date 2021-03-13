package io.jnyou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.AddressPool;
import io.jnyou.mapper.AddressPoolMapper;
import io.jnyou.service.AddressPoolService;
import org.springframework.stereotype.Service;

@Service
public class AddressPoolServiceImpl extends ServiceImpl<AddressPoolMapper, AddressPool> implements AddressPoolService {

}

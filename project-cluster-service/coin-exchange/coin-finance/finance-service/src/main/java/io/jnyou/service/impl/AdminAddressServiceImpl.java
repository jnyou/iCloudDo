package io.jnyou.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jnyou.domain.AdminAddress;
import io.jnyou.domain.Coin;
import io.jnyou.mapper.AdminAddressMapper;
import io.jnyou.service.AdminAddressService;
import io.jnyou.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAddressServiceImpl extends ServiceImpl<AdminAddressMapper, AdminAddress> implements AdminAddressService {

    @Autowired
    private CoinService coinService ;
    /**
     * 条件分页查询归集地址
     *
     * @param page   分页参数
     * @param coinId 币种的Id
     * @return
     */
    @Override
    public Page<AdminAddress> findByPage(Page<AdminAddress> page, Long coinId) {
        return page(page,new LambdaQueryWrapper<AdminAddress>().eq(coinId!=null ,AdminAddress::getCoinId,coinId));
    }

    /**
     * 重新save ,为了让我们的归集地址里面包含coinType
     * @param entity
     * @return
     */
    @Override
    public boolean save(AdminAddress entity) {
        Long coinId = entity.getCoinId();
        Coin coin = coinService.getById(coinId);
        if(coin==null){
            throw new IllegalArgumentException("输入的币种id错误") ;
        }
        String type = coin.getType();
        entity.setCoinType(type);
        return super.save(entity) ;
    }
}

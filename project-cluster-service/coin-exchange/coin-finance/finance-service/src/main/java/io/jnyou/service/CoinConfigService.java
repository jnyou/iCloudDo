package io.jnyou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.jnyou.domain.CoinConfig;

public interface CoinConfigService extends IService<CoinConfig> {


    /**
     * 通过币种的id 查询币种的配置信息
     * @param coinId
     * 币种的id
     * @return
     * 币种的配置信息
     */
    CoinConfig findByCoinId(Long coinId);

    /**
     * 新增或修改币种配置
     * @param coinConfig
     * @return
     */
    boolean updateOrSave(CoinConfig coinConfig);
}


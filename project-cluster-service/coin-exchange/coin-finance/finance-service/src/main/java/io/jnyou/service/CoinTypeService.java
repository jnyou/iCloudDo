package io.jnyou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jnyou.domain.CoinType;

import java.util.List;

public interface CoinTypeService extends IService<CoinType> {


    /**
     * 条件分页查询货币类型
     * @param page
     * 分页参数
     * @param code
     * 类型的Code
     * @return
     * 分页货币类型
     */
    Page<CoinType> findByPage(Page<CoinType> page, String code);

    /**
     * 使用币种类型的状态查询所有的币种类型值
     * @param status
     * @return
     */
    List<CoinType> listByStatus(Byte status);
}


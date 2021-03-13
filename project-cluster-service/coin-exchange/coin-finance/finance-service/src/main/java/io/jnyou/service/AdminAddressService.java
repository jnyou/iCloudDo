package io.jnyou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jnyou.domain.AdminAddress;

public interface AdminAddressService extends IService<AdminAddress>{


    /**
     * 条件分页查询归集地址
     * @param page
     * 分页参数
     * @param coinId
     * 币种的Id
     * @return
     */
    Page<AdminAddress> findByPage(Page<AdminAddress> page, Long coinId);
}

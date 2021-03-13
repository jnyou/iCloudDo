package io.jnyou.feign;

import io.jnyou.config.feign.FeignConfig;
import io.jnyou.dto.AdminBankDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * AdminBankServiceFeign
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient(name = "admin-service",path = "/adminBanks",configuration = FeignConfig.class)
public interface AdminBankServiceFeign {

    @GetMapping("/list")
    List<AdminBankDto> getAllAdminBanks() ;
}
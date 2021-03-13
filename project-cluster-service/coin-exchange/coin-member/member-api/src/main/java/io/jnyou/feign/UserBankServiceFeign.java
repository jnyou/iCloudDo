package io.jnyou.feign;


import io.jnyou.config.feign.FeignConfig;
import io.jnyou.dto.UserBankDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 若FeignClient 里面的name 相同时,spring 创建对象就会报错:它认为它们2 个对象是一样的
 */
@FeignClient(name = "coin-member-service", contextId = "userBankServiceFeign", configuration = FeignConfig.class, path = "/userBanks")
public interface UserBankServiceFeign {

    @GetMapping("/{userId}/info")
    UserBankDto getUserBankInfo(@PathVariable Long userId);
}

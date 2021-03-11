package io.jnyou.feign;

import io.jnyou.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * UserFeignClient
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@FeignClient(value = "coin-member-service",configuration = FeignClient.class,path = "/users")
public interface UserFeignClient {

    /**
     * 用于admin-service 里面远程调用member-service
     * @param ids
     * @return
     */
    @GetMapping("/basic/users")
    List<UserDto> userDtoList(@RequestParam("ids") List<Long> ids);

}
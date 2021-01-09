package io.jnyou.aop.controller;

import io.jnyou.aop.annotation.VisitPermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author JnYou
 * @version 1.0.0
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
    /**
     * 配置权限注解 @VisitPermission("permission-test")
     * 只用拥有该权限的用户才能访问，否则提示非法操作
     */
    @VisitPermission("permission-test")
    @GetMapping("/test")
    public String test() {
        System.out.println("================== step 3: doing ==================");
        return "success";
    }
}

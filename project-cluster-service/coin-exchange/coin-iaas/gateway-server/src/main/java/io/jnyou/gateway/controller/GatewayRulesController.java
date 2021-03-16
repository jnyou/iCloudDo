package io.jnyou.gateway.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * GatewayRulesController
 * 网关限流策略
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@RestController
public class GatewayRulesController {

    /**
     * 获取当前系统的限流策略
     */
    @GetMapping("/gw/flow/rules")
    public Set<GatewayFlowRule> getCurrentGatewayFlowRules() {
        return GatewayRuleManager.getRules();
    }

    /**
     * 获取我定义的api分组
     */
    @GetMapping("/gw/api/groups")
    public Set<ApiDefinition> getApiGroups() {
        return GatewayApiDefinitionManager.getApiDefinitions();
    }
}
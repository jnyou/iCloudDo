package io.jnyou.match;

import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * MatchServiceFactory
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class MatchServiceFactory implements InitializingBean {


    private static final Map<MatchStrategy,MatchService> matchServiceMap = new ConcurrentHashMap<>();

    /**
     * 给我们的策略工厂里面添加一个交易的实现类型
     * @param matchStrategy
     * @param matchService
     */
    public static void setMatchServiceMap(MatchStrategy matchStrategy,MatchService matchService) {
        matchServiceMap.put(matchStrategy,matchService);
    }

    /**
     * 使用策略的名称获取具体的实现类
     * @param matchStrategy
     * @return
     */
    public static MatchService getMatchService(MatchStrategy matchStrategy) {
        return matchServiceMap.get(matchStrategy);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
//        setMatchServiceMap();
    }
}
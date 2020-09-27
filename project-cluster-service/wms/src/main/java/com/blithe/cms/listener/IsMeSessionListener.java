package com.blithe.cms.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName yjn
 * @Description: MySessionListener监听类
 * @Author: 夏小颜
 * @Version: 1.0
 **/

@Component
public class IsMeSessionListener implements SessionListener {

    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override
    public void onStart(Session session) {
        sessionCount.incrementAndGet();
        //System.out.println("登录+1=="+sessionCount.get());
    }

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
        //System.out.println("登录退出-1=="+sessionCount.get());
    }

    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();
        //System.out.println("登录过期-1=="+sessionCount.get());
    }

    public int getSessionCount() {
        return sessionCount.get();
    }
}
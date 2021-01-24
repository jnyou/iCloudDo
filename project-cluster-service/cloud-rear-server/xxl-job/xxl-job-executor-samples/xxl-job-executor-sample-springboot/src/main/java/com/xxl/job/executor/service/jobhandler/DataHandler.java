package com.xxl.job.executor.service.jobhandler;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONObjectIter;
import com.google.gson.JsonObject;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * DataHandler
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Component
public class DataHandler {

    private static Logger logger = LoggerFactory.getLogger(SampleXxlJob.class);

    @XxlJob("memberJobHandler")
    public void getMemberJobHandler() {
        XxlJobHelper.log("XXL-JOB, Hello World.");
        HttpRequest request = HttpRequest.get("http://192.168.137.121:7000/shop/coupon/member");
        String result = request.execute().body();
        System.out.println(result);
        logger.info(result);
    }

}
package com.blithe.cms.common.tools;

import com.alibaba.fastjson.JSONObject;


/**
 * Description
 * Created by crazy
 * Created on 2018/11/5.
 */
public class JsonResultUtil {
    public static int SUCCESS_NUM = 0;
    public static String SUCCESS_MSG = "成功";

    public static int ERR_NUM = 1;
    public static String ERR_MSG = "接口错误";

    public static JSONObject getResultJSON(int code, String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errCode", code);
        jsonObject.put("remark", message);
        return jsonObject;
    }
}

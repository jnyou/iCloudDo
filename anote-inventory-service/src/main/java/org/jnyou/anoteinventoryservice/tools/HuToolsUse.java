package org.jnyou.anoteinventoryservice.tools;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * HuToolUse
 * Hutool v4.5.15
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class HuToolsUse {

    public static void main(String[] args) {
//        useDate();
//        test();
        industryKeyword();
    }


    public static void industryKeyword() {

        String industryRes = "、招聘类[简历]、教育类[简历]";
        System.out.println(industryRes.split("、").length);

        HashMap<String,Integer> map = new HashMap<>();
        map.put("招聘类",5);
        map.put("教育类",1);
        System.out.println(map.keySet());
        System.out.println(map.entrySet());

    }

    /**
     * 时间日期
     * @Author JnYou
     */
    public static void useDate() {
        // 当前时间
        DateTime date = DateUtil.date();
        // 当前时间
        DateTime date1 = DateUtil.date(Calendar.getInstance());
        //当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());

        // 当前时间字符串 格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        // 当前日期字符串 格式：yyyy-MM-dd
        String today = DateUtil.today();

        DateTime dateTime = DateUtil.lastWeek();
        System.out.println(dateTime);

        DateTime dateTime1 = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);
        //结果：2017-01-05 12:34:23
        String dateStr = dateTime1.toString();
        System.out.println(dateStr);

        //请求列表页
        String listContent = HttpUtil.get("https://www.oschina.net/action/ajax/get_more_news_list?newsType=&p=2");
        //使用正则获取所有标题
        List<String> titles = ReUtil.findAll("<span class=\"text-ellipsis\">(.*?)</span>", listContent, 1);
        for (String title : titles) {
            //打印标题
            Console.log(title);
        }
    }


    public static void test() {

        String st = "傻逼|操你妈|你大爷|弄死你|神经病|弄死|滚蛋|杀死你|烧死你全家|上门催收|搞死你|草泥马|滚犊子|去你妈的|麻痹|狗日的|混蛋|叫爹|有病|不要打了|烦死了|投诉|法务部|傻逼呵呵|2b|2逼|2比|2笔|二b|二逼|二比|二笔|他妈的|他妈个逼|报警|骚扰|退党|法轮功|退团|反共|共产党|你妈|死了|牛逼|你他妈|王八蛋|鸡巴|砸死你|滚吧|挂靠";
        String[] split = st.split("\\|");
        List<String> keywords = Arrays.asList(split);
        boolean include = Pattern.matches(".*(" + String.join("|", keywords) + ").*", "我又不是建筑行业的，我就查社保，他说话过不了的呀");
        System.out.println(include);
    }



}
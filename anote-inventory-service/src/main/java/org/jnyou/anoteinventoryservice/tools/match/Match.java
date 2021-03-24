package org.jnyou.anoteinventoryservice.tools.match;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * Match
 *
 * @version 1.0.0
 * @author: JnYou
 **/
public class Match {

    public static void main(String[] args) {
        String source = "<!DOCTYPE HTML PUBLIC \\\"-//IETF//DTD HTML 2.0//EN\\\"><html><head><title>201 CREATED</title></head><body><h1>TGT Created</h1><form action=\"http://sso.cloud.hchliot.com/cas/v1/tickets/TGT-389-5kDbnbTZgFl5kO-yjunn6868E5BW6ATBi6-zVvkMBLo9-K2rGOOCPDo-Uxgn64V64pM-sso-fc795f596-s8l7r\" method=\"POST\">Service:<input type=\"text\" name=\"service\" value=\"\"><br><input type=\"submit\" value=\"Submit\"></form></body></html>";
        match(source,"form","action");
    }

    /**
     * 匹配到表单中action的URL地址
     * @param source
     * @param element
     * @param attr
     * @return
     */
    public static String match(String source, String element, String attr) {
        String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?(\\s.*?)?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            return r;
        }
        return "";
    }

}
package com.blithe.cms.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName Query
 * @Description: 分页查询信息
 * @Author: 夏小颜
 * @Date: 12:12
 * @Version: 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Integer page;
    /**
     * 每页条数
     */
    private Integer limit;

    public Query(Map<String,Object> params) throws Exception {
        this.putAll(params);

        //分页参数
        this.page = StringUtil.getInt(params.get("page"), 1);
        this.limit = StringUtil.getInt(params.get("limit"), 20);
        //mysql查询，起始位置；
        this.put("offset", (page - 1) * limit);
        //每页条数
        this.put("limit", limit);
        this.put("page", page);

        //防止SQL注入（因为sort、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sort = StringUtil.getString(params.get("sort"));
        String order = StringUtil.getString(params.get("order"));
        this.put("sort", SqlFilter.sqlInject(sort));
        this.put("order", SqlFilter.sqlInject(order));
    }


}
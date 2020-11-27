package com.blithe.cms.common.tools;


import com.blithe.cms.common.tools.xss.SQLFilter;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 * @author jnyou
 */
@Data
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	//当前页码
    private int page;
    //每页条数
    private int limit;

    public Query(Map<String, Object> params) throws Exception{
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
        this.put("sort", SQLFilter.sqlInject(sort));
        this.put("order", SQLFilter.sqlInject(order));
    }
}

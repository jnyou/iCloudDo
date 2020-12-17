package org.jnyou.gmall.search.service;

import org.jnyou.gmall.search.vo.SearchParam;

public interface MallSearchService {
    /**
     *
     * @param searchParam 检索的所有参数
     * @Author JnYou
     */
    Object search(SearchParam searchParam);
}

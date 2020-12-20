package org.jnyou.gmall.search.service;

import org.jnyou.gmall.search.vo.SearchParam;
import org.jnyou.gmall.search.vo.SearchResult;

public interface MallSearchService {
    /**
     *
     * @param searchParam 检索的所有参数
     * @Author JnYou
     * @return SearchResult
     */
    SearchResult search(SearchParam searchParam);
}

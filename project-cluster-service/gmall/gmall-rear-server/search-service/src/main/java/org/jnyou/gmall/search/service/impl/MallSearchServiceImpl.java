package org.jnyou.gmall.search.service.impl;

import com.sun.xml.internal.ws.api.policy.SourceModel;
import com.sun.xml.internal.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
import lombok.experimental.Accessors;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.jnyou.gmall.search.config.ElasticSearchConfig;
import org.jnyou.gmall.search.constant.EsConstant;
import org.jnyou.gmall.search.service.MallSearchService;
import org.jnyou.gmall.search.vo.SearchParam;
import org.jnyou.gmall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * <p>
 *
 * @className MallSearchServiceImpl
 * @author: JnYou xiaojian19970910@gmail.com
 **/
@Service
public class MallSearchServiceImpl implements MallSearchService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public SearchResult search(SearchParam param) {

        SearchResult result = null;
        // 动态构建DSL查询语句
        SearchRequest searchRequest = buildSearchRequest(param);

        try {
            // 执行检索请求
            SearchResponse response = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            // 分析响应数据进行封装成想要的数据格式
            buildSearchResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 构建准备检索请求：模糊匹配，过滤（按属性，分类，品牌，价格区间，库存），排序，分页，高亮，聚合分析
     * @Author JnYou
     */
    @SuppressWarnings(value = "unchecked")
    private SearchRequest buildSearchRequest(SearchParam param) {
        // 构造检索条件(dsl) 封装的检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 模糊匹配，过滤（按属性，分类，品牌，价格区间，库存）
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if(!StringUtils.isEmpty(param.getKeyword())){
            queryBuilder.must(QueryBuilders.matchQuery("skuTitle",param.getKeyword()));
        }
        if(!StringUtils.isEmpty(param.getCatalog3Id())){
            queryBuilder.filter(QueryBuilders.termQuery("catalogId",param.getCatalog3Id()));
        }
        if(!StringUtils.isEmpty(param.getBrandId()) && param.getBrandId().size() > 0){
            queryBuilder.filter(QueryBuilders.termsQuery("brandId",param.getBrandId()));
        }
        // 属性查询
        if(!CollectionUtils.isEmpty(param.getAttrs()) && param.getAttrs().size() > 0){
            // 格式： 2_6寸:5.5寸
            for (String attr : param.getAttrs()) {
                BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
                String[] s = attr.split("_");
                // 属性ID
                String attrId = s[0];
                // 属性值
                String[] attrValue = s[1].split(":");
                boolQuery.must(QueryBuilders.termQuery("attrs.attrId",attrId));
                boolQuery.must(QueryBuilders.termsQuery("attrs.attrValue",attrValue));
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", boolQuery, ScoreMode.None);
                queryBuilder.filter(nestedQuery);
            }
        }

        // 是否有库存
        queryBuilder.filter(QueryBuilders.termQuery("hasStock",param.getHasStock() == 1));
        // 价格区间
        if(!StringUtils.isEmpty(param.getSkuPrice())){
            // 格式：1_500/_500/500_
            RangeQueryBuilder skuPrice = QueryBuilders.rangeQuery("skuPrice");
            String[] s = param.getSkuPrice().split("_");
            if(s.length == 2){
                // 区间
                skuPrice.gte(s[0]).lte(s[1]);
            } else if (s.length == 1){
                if(param.getSkuPrice().startsWith("_")){
                    skuPrice.lte(s[0]);
                }
                if(param.getSkuPrice().endsWith("_")){
                    skuPrice.gte(s[0]);
                }
            }
            queryBuilder.filter(skuPrice);
        }
        // 放入查询条件
        searchSourceBuilder.query(queryBuilder);
        // 排序
        if(!StringUtils.isEmpty(param.getSort())){
            // 规则：sort=price_desc/asc
            String[] s = param.getSort().split("_");
            searchSourceBuilder.sort(s[0], SortOrder.valueOf(s[1]));
        }
        // 分页
        searchSourceBuilder.from((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGESIZE);
        searchSourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);
        // 高亮
        if(!StringUtils.isEmpty(param.getKeyword())){
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        System.out.println("构建的DSL语句：" + searchSourceBuilder);
        // 聚合分析

        // 创建索引请求
        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, searchSourceBuilder);
        return searchRequest;
    }

    /**
     * 构建结果数据
     * @Author JnYou
     */
    private void buildSearchResponse() {
    }


}
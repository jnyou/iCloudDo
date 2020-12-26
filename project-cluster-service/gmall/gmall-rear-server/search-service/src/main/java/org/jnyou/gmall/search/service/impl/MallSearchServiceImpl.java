package org.jnyou.gmall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.jnyou.common.to.es.SkuEsModel;
import org.jnyou.common.utils.R;
import org.jnyou.gmall.search.config.ElasticSearchConfig;
import org.jnyou.gmall.search.constant.EsConstant;
import org.jnyou.gmall.search.feign.ProductFeignClient;
import org.jnyou.gmall.search.service.MallSearchService;
import org.jnyou.gmall.search.vo.AttrResponseVo;
import org.jnyou.gmall.search.vo.SearchParam;
import org.jnyou.gmall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public SearchResult search(SearchParam param) {

        SearchResult result = null;
        // 动态构建DSL查询语句
        SearchRequest searchRequest = buildSearchRequest(param);

        try {
            // 执行检索请求
            SearchResponse response = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);

            // 分析响应数据进行封装成想要的数据格式
            result = buildSearchResponse(response, param);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 构建准备检索请求：模糊匹配，过滤（按属性，分类，品牌，价格区间，库存），排序，分页，高亮，聚合分析
     * http://search.gmall.com/list.html?keyword=Apple&sort=saleCount_desc&hasStock=0&skuPrice=0_7000&brandId=8&catalog3Id=225&attrs=8_A2404:A2404
     *
     * @Author JnYou
     */
    @SuppressWarnings(value = "unchecked")
    private SearchRequest buildSearchRequest(SearchParam param) {
        // 构造检索条件(dsl) 封装的检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 模糊匹配，过滤（按属性，分类，品牌，价格区间，库存）
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(param.getKeyword())) {
            queryBuilder.must(QueryBuilders.matchQuery("skuTitle", param.getKeyword()));
        }
        if (!StringUtils.isEmpty(param.getCatalog3Id())) {
            queryBuilder.filter(QueryBuilders.termQuery("catalogId", param.getCatalog3Id()));
        }
        if (!StringUtils.isEmpty(param.getBrandId()) && param.getBrandId().size() > 0) {
            queryBuilder.filter(QueryBuilders.termsQuery("brandId", param.getBrandId()));
        }
        // 属性查询
        if (!CollectionUtils.isEmpty(param.getAttrs()) && param.getAttrs().size() > 0) {
            // 格式： 2_6寸:5.5寸
            for (String attr : param.getAttrs()) {
                BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
                String[] s = attr.split("_");
                // 属性ID
                String attrId = s[0];
                // 属性值
                String[] attrValue = s[1].split(":");
                boolQuery.must(QueryBuilders.termQuery("attrs.attrId", attrId));
                boolQuery.must(QueryBuilders.termsQuery("attrs.attrValue", attrValue));
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("attrs", boolQuery, ScoreMode.None);
                queryBuilder.filter(nestedQuery);
            }
        }

        // 是否有库存
        if (null != param.getHasStock()) {
            queryBuilder.filter(QueryBuilders.termQuery("hasStock", param.getHasStock() == 1));
        }
        // 价格区间
        if (!StringUtils.isEmpty(param.getSkuPrice())) {
            // 格式：1_500/_500/500_
            RangeQueryBuilder skuPrice = QueryBuilders.rangeQuery("skuPrice");
            String[] s = param.getSkuPrice().split("_");
            if (s.length == 2) {
                // 区间
                skuPrice.gte(s[0]).lte(s[1]);
            } else if (s.length == 1) {
                if (param.getSkuPrice().startsWith("_")) {
                    skuPrice.lte(s[0]);
                }
                if (param.getSkuPrice().endsWith("_")) {
                    skuPrice.gte(s[0]);
                }
            }
            queryBuilder.filter(skuPrice);
        }
        // 放入查询条件
        searchSourceBuilder.query(queryBuilder);
        // 排序
        if (!StringUtils.isEmpty(param.getSort())) {
            // 规则：sort=price_desc/asc
            String[] s = param.getSort().split("_");
            searchSourceBuilder.sort(s[0], s[1].equalsIgnoreCase("desc") ? SortOrder.DESC : SortOrder.ASC);
        }
        // 分页
        searchSourceBuilder.from((param.getPageNum() - 1) * EsConstant.PRODUCT_PAGESIZE);
        searchSourceBuilder.size(EsConstant.PRODUCT_PAGESIZE);
        // 高亮
        if (!StringUtils.isEmpty(param.getKeyword())) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuTitle");
            highlightBuilder.preTags("<b style='color:red'>");
            highlightBuilder.postTags("</b>");
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        // 聚合分析

        // 品牌聚合
        TermsAggregationBuilder brandAgg = AggregationBuilders.terms("brandAgg").field("brandId").size(50);
        // 子聚合品牌名称
        brandAgg.subAggregation(AggregationBuilders.terms("brandNameAgg").field("brandName").size(1));
        // 子聚合品牌图片
        brandAgg.subAggregation(AggregationBuilders.terms("brandImgAgg").field("brandImg").size(1));
        searchSourceBuilder.aggregation(brandAgg);

        // 分类聚合
        TermsAggregationBuilder catalogAgg = AggregationBuilders.terms("catalogAgg").field("catalogId").size(20);
        // 分类的子聚合
        TermsAggregationBuilder builder = AggregationBuilders.terms("catalogNameAgg").field("catalogName").size(1);
        catalogAgg.subAggregation(builder);
        searchSourceBuilder.aggregation(catalogAgg);

        // 属性聚合
        NestedAggregationBuilder nested = AggregationBuilders.nested("attrAgg", "attrs");
        // 属性
        TermsAggregationBuilder attrIdAgg = AggregationBuilders.terms("attrIdAgg").field("attrs.attrId");
        // 属性名称
        attrIdAgg.subAggregation(AggregationBuilders.terms("attrNameAgg").field("attrs.attrName").size(1));
        // 属性值
        attrIdAgg.subAggregation(AggregationBuilders.terms("attrValueAgg").field("attrs.attrValue").size(50));

        nested.subAggregation(attrIdAgg);

        searchSourceBuilder.aggregation(nested);

        System.out.println("构建的DSL语句：" + searchSourceBuilder);
        /**
         * 构建的DSL语句：{"from":0,"size":2,"query":{"bool":{"must":[{"match":{"skuTitle":{"query":"Apple","operator":"OR","prefix_length":0,"max_expansions":50,"fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","auto_generate_synonyms_phrase_query":true,"boost":1.0}}}],"filter":[{"term":{"catalogId":{"value":225,"boost":1.0}}},{"terms":{"brandId":[8],"boost":1.0}},{"nested":{"query":{"bool":{"must":[{"term":{"attrs.attrId":{"value":"8","boost":1.0}}},{"terms":{"attrs.attrValue":["A2404","A2405"],"boost":1.0}}],"adjust_pure_negative":true,"boost":1.0}},"path":"attrs","ignore_unmapped":false,"score_mode":"none","boost":1.0}},{"term":{"hasStock":{"value":false,"boost":1.0}}},{"range":{"skuPrice":{"from":"0","to":"7000","include_lower":true,"include_upper":true,"boost":1.0}}}],"adjust_pure_negative":true,"boost":1.0}},"sort":[{"saleCount":{"order":"desc"}}],"aggregations":{"brandAgg":{"terms":{"field":"brandId","size":50,"min_doc_count":1,"shard_min_doc_count":0,"show_term_doc_count_error":false,"order":[{"_count":"desc"},{"_key":"asc"}]},"aggregations":{"brandNameAgg":{"terms":{"field":"brandName","size":1,"min_doc_count":1,"shard_min_doc_count":0,"show_term_doc_count_error":false,"order":[{"_count":"desc"},{"_key":"asc"}]}},"brandImgAgg":{"terms":{"field":"brandImg","size":1,"min_doc_count":1,"shard_min_doc_count":0,"show_term_doc_count_error":false,"order":[{"_count":"desc"},{"_key":"asc"}]}}}},"catalogAgg":{"terms":{"field":"catalogId","size":20,"min_doc_count":1,"shard_min_doc_count":0,"show_term_doc_count_error":false,"order":[{"_count":"desc"},{"_key":"asc"}]},"aggregations":{"catalogNameAgg":{"terms":{"field":"catalogName","size":1,"min_doc_count":1,"shard_min_doc_count":0,"show_term_doc_count_error":false,"order":[{"_count":"desc"},{"_key":"asc"}]}}}},"attrAgg":{"nested":{"path":"attrs"},"aggregations":{"attrIdAgg":{"terms":{"field":"attrs.attrId","size":10,"min_doc_count":1,"shard_min_doc_count":0,"show_term_doc_count_error":false,"order":[{"_count":"desc"},{"_key":"asc"}]},"aggregations":{"attrNameAgg":{"terms":{"field":"attrs.attrName","size":1,"min_doc_count":1,"shard_min_doc_count":0,"show_term_doc_count_error":false,"order":[{"_count":"desc"},{"_key":"asc"}]}},"attrValueAgg":{"terms":{"field":"attrs.attrValue","size":50,"min_doc_count":1,"shard_min_doc_count":0,"show_term_doc_count_error":false,"order":[{"_count":"desc"},{"_key":"asc"}]}}}}}}},"highlight":{"pre_tags":["<b style='color:red'>"],"post_tags":["</b>"],"fields":{"skuTitle":{}}}}
         */
        // 创建索引请求
        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.PRODUCT_INDEX}, searchSourceBuilder);
        return searchRequest;
    }

    /**
     * 构建结果数据
     *
     * @Author JnYou
     */
    private SearchResult buildSearchResponse(SearchResponse response, SearchParam param) {
        System.out.println("返回的数据：" + response);
        /**
         * 返回的数据：{"took":7,"timed_out":false,"_shards":{"total":1,"successful":1,"skipped":0,"failed":0},"hits":{"total":{"value":9,"relation":"eq"},"max_score":null,"hits":[{"_index":"gm_product","_type":"_doc","_id":"8","_score":null,"_source":{"attrs":[{"attrId":8,"attrName":"入网型号","attrValue":"A2404"},{"attrId":9,"attrName":"产品名称","attrValue":"iPhone 12"},{"attrId":16,"attrName":"主屏幕尺寸（英寸）","attrValue":"6.1英寸"}],"brandId":8,"brandImg":"https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/9124f36e-2d6f-445a-807c-92c23e84a23b_ccd1077b985c7150.jpg","brandName":"Apple iPhone 12","catalogId":225,"catalogName":"手机","hasStock":false,"hotScore":0,"saleCount":0,"skuId":8,"skuImg":"https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg","skuPrice":6799.0,"skuTitle":"Apple iPhone 12 黑色全网通5G手机双卡双待 128GB","spuId":10},"highlight":{"skuTitle":["<b style='color:red'>Apple</b> iPhone 12 黑色全网通5G手机双卡双待 128GB"]},"sort":[0]},{"_index":"gm_product","_type":"_doc","_id":"10","_score":null,"_source":{"attrs":[{"attrId":8,"attrName":"入网型号","attrValue":"A2404"},{"attrId":9,"attrName":"产品名称","attrValue":"iPhone 12"},{"attrId":16,"attrName":"主屏幕尺寸（英寸）","attrValue":"6.1英寸"}],"brandId":8,"brandImg":"https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/9124f36e-2d6f-445a-807c-92c23e84a23b_ccd1077b985c7150.jpg","brandName":"Apple iPhone 12","catalogId":225,"catalogName":"手机","hasStock":false,"hotScore":0,"saleCount":0,"skuId":10,"skuImg":"https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/d1624201-aed6-447d-8e22-f309750ccc45_5b5e74d0978360a1.jpg","skuPrice":6299.0,"skuTitle":"Apple iPhone 12 红色全网通5G手机双卡双待 64GB","spuId":10},"highlight":{"skuTitle":["<b style='color:red'>Apple</b> iPhone 12 红色全网通5G手机双卡双待 64GB"]},"sort":[0]}]},"aggregations":{"nested#attrAgg":{"doc_count":27,"lterms#attrIdAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":8,"doc_count":9,"sterms#attrNameAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":"入网型号","doc_count":9}]},"sterms#attrValueAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":"A2404","doc_count":9}]}},{"key":9,"doc_count":9,"sterms#attrNameAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":"产品名称","doc_count":9}]},"sterms#attrValueAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":"iPhone 12","doc_count":9}]}},{"key":16,"doc_count":9,"sterms#attrNameAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":"主屏幕尺寸（英寸）","doc_count":9}]},"sterms#attrValueAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":"6.1英寸","doc_count":9}]}}]}},"lterms#brandAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":8,"doc_count":9,"sterms#brandImgAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":"https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/9124f36e-2d6f-445a-807c-92c23e84a23b_ccd1077b985c7150.jpg","doc_count":9}]},"sterms#brandNameAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":"Apple iPhone 12","doc_count":9}]}}]},"lterms#catalogAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":225,"doc_count":9,"sterms#catalogNameAgg":{"doc_count_error_upper_bound":0,"sum_other_doc_count":0,"buckets":[{"key":"手机","doc_count":9}]}}]}}}
         */
        SearchResult searchResult = new SearchResult();
        SearchHits hits = response.getHits();
        // 总记录数
        long total = hits.getTotalHits().value;
        searchResult.setTotal(total);
        // 总页码
        int page = ((int) total % EsConstant.PRODUCT_PAGESIZE) == 0 ? ((int) total / EsConstant.PRODUCT_PAGESIZE) : ((int) total / EsConstant.PRODUCT_PAGESIZE + 1);
        searchResult.setTotalPages(page);
        // 当前页码
        searchResult.setPageNum(param.getPageNum());
        // 组装一个页码集合
        List<Integer> pageNavs = new ArrayList<>();
        for (int i = 1; i <= page; i++) {
            pageNavs.add(i);
        }
        searchResult.setPageNavs(pageNavs);
        // 商品信息
        List<SkuEsModel> skuEsModels = new ArrayList<>();
        if (null != hits.getHits() && hits.getHits().length > 0) {
            for (SearchHit hit : hits.getHits()) {
                String sourceAsString = hit.getSourceAsString();
                SkuEsModel skuEsModel = JSON.parseObject(sourceAsString, SkuEsModel.class);
                if (!StringUtils.isEmpty(param.getKeyword())) {
                    // 设置高亮
                    HighlightField skuTitle = hit.getHighlightFields().get("skuTitle");
                    String string = skuTitle.fragments()[0].string();
                    skuEsModel.setSkuTitle(string);
                }
                skuEsModels.add(skuEsModel);
            }
        }
        searchResult.setProduct(skuEsModels);

        // 分类的聚合信息封装
        ParsedLongTerms catalogAgg = response.getAggregations().get("catalogAgg");
        List<? extends Terms.Bucket> buckets = catalogAgg.getBuckets();
        List<SearchResult.CatalogVo> catalogVos = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            SearchResult.CatalogVo catalogVo = new SearchResult.CatalogVo();
            String keyAsString = bucket.getKeyAsString();
            catalogVo.setCatalogId(Long.parseLong(keyAsString));
            ParsedStringTerms parsedStringTerms = bucket.getAggregations().get("catalogNameAgg");
            String catalogName = parsedStringTerms.getBuckets().get(0).getKeyAsString();
            catalogVo.setCatalogName(catalogName);
            catalogVos.add(catalogVo);
        }
        searchResult.setCatalogs(catalogVos);

        // 品牌聚合信息封装
        List<SearchResult.BrandVo> brandVos = new ArrayList<>();
        ParsedLongTerms parsedLongTerms = response.getAggregations().get("brandAgg");
        for (Terms.Bucket bucket : parsedLongTerms.getBuckets()) {
            SearchResult.BrandVo brandVo = new SearchResult.BrandVo();
            // 品牌ID
            long brandId = bucket.getKeyAsNumber().longValue();
            brandVo.setBrandId(brandId);
            // 品牌的名称
            ParsedStringTerms parsedStringTerms = bucket.getAggregations().get("brandNameAgg");
            String brandName = parsedStringTerms.getBuckets().get(0).getKeyAsString();
            brandVo.setBrandName(brandName);
            // 品牌的图片
            ParsedStringTerms parsedStringTermsImg = bucket.getAggregations().get("brandImgAgg");
            String brandImg = parsedStringTermsImg.getBuckets().get(0).getKeyAsString();
            brandVo.setBrandImg(brandImg);
            brandVos.add(brandVo);
        }
        searchResult.setBrands(brandVos);

        // 属性聚合信息封装
        List<SearchResult.AttrVo> attrVos = new ArrayList<>();
        ParsedNested parsedNested = response.getAggregations().get("attrAgg");
        // 属性ID
        ParsedLongTerms attrIdAgg = parsedNested.getAggregations().get("attrIdAgg");
        for (Terms.Bucket bucket : attrIdAgg.getBuckets()) {
            SearchResult.AttrVo attrVo = new SearchResult.AttrVo();
            long attrId = bucket.getKeyAsNumber().longValue();
            attrVo.setAttrId(attrId);
            // 属性名称
            ParsedStringTerms parsedStringTerms = bucket.getAggregations().get("attrNameAgg");
            String attrName = parsedStringTerms.getBuckets().get(0).getKeyAsString();
            attrVo.setAttrName(attrName);

            ParsedStringTerms attrValueAgg = bucket.getAggregations().get("attrValueAgg");
            List<String> attrValues = attrValueAgg.getBuckets().stream().map(item -> {
                return item.getKeyAsString();
            }).collect(Collectors.toList());

            attrVo.setAttrValue(attrValues);
            attrVos.add(attrVo);
        }

        searchResult.setAttrs(attrVos);

        // 构建面包屑导航功能
        if(null != param.getAttrs() && param.getAttrs().size() > 0){
            List<SearchResult.NavVo> collect = param.getAttrs().stream().map(attr -> {
                SearchResult.NavVo navVo = new SearchResult.NavVo();
                // 1_其他:安卓
                String[] s = attr.split("_");
                navVo.setNavValue(s[1]);
                R r = productFeignClient.attrInfo(Long.parseLong(s[0]));
                if(r.getCode() == 0){
                    AttrResponseVo data = r.getData("attr", new TypeReference<AttrResponseVo>() {
                    });
                    navVo.setNavName(data.getAttrName());
                } else {
                    // 调用失败后给个默认值
                    navVo.setNavName(s[0]);
                }
                // 取消面包屑以后，需要跳转的路径地址，将请求地址的URL条件置空
                String encode = null;
                try {
                    encode = URLEncoder.encode(attr,"UTF-8");
                    // 浏览器对空格编码和java不一样
                    encode = encode.replace("+","%20");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String replace = param.get_queryString().replace("&attrs=" + encode, "");
                navVo.setLink("http://search.gmall.com?" + replace);
                return navVo;
            }).collect(Collectors.toList());
            searchResult.setNavs(collect);
        }

        // 品牌、分类上面包屑
        if(null != param.getBrandId() && param.getBrandId().size() > 0){
            List<SearchResult.NavVo> navVos = new ArrayList<>();
            SearchResult.NavVo navVo = new SearchResult.NavVo();
            navVo.setNavName("品牌");

            navVos.add(navVo);
            searchResult.setNavs(navVos);

        }


        return searchResult;
    }


}
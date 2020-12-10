package org.jnyou.gmall.search.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.jnyou.common.to.es.SkuEsModel;
import org.jnyou.gmall.search.config.ElasticSearchConfig;
import org.jnyou.gmall.search.constant.EsConstant;
import org.jnyou.gmall.search.service.ElasticSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * @ClassName ElasticSaveServiceImpl
 * @Author: JnYou
 **/
@Slf4j
@Service
public class ElasticSaveServiceImpl implements ElasticSaveService {

    @Autowired
    private RestHighLevelClient client;

    @Override
    public boolean productUp(List<SkuEsModel> skuEsModels) throws IOException {
        // 保存数据到es中
        // 1、给es中建立索引。product 建立好映射关系.

        // 给es中保存
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel model : skuEsModels) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            // 使用json字符串存储数据
            String jsonString = JSON.toJSONString(model);
            indexRequest.source(jsonString, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        // 批量操作进行存储
        BulkResponse bulk = client.bulk(bulkRequest, ElasticSearchConfig.COMMON_OPTIONS);

        // TODO 如果批量储存错误
        boolean failures = bulk.hasFailures();
        List<String> collect = Arrays.asList(bulk.getItems()).stream().map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.info("商品上架成功：{}，返回数据：{}" + collect,bulk.toString());
        return failures;
    }
}
package org.jnyou.gmall.search;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.ToString;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.jnyou.gmall.search.config.ElasticSearchConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SearchServiceApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    void contextLoads() {
        System.out.println(client);
    }

    /**
     * es检索数据
     *
     * @Author JnYou
     */
    @Test
    void searchData() throws IOException {

        // 创建索引请求
        SearchRequest searchRequest = new SearchRequest();
        // 指定检索的索引
        searchRequest.indices("bank");

        // 构造检索条件 封装的检索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query();
//        searchSourceBuilder.from();
//        searchSourceBuilder.size();
        searchSourceBuilder.query(QueryBuilders.matchQuery("address", "mill"));
        // 聚合查询
        searchSourceBuilder.aggregation(AggregationBuilders.terms("ageAgg").field("age").size(10))
                .aggregation(AggregationBuilders.avg("ageAvg").field("age"))
                .aggregation(AggregationBuilders.avg("balanceAvg").field("balance"));
        System.out.println("检索条件" + searchSourceBuilder.toString());

        // 指定DSL检索条件
        searchRequest.source(searchSourceBuilder);

        /**
         * 检索条件
         * GET bank/_search
         * {
         *     "query":{
         *         "match":{
         *             "address":{
         *                 "query":"mill"
         *             }
         *         }
         *     },
         *     "aggregations":{
         *         "ageAgg":{
         *             "terms":{
         *                 "field":"age",
         *                 "size":10
         *             }
         *         },
         *         "ageAvg":{
         *             "avg":{
         *                 "field":"age"
         *             }
         *         },
         *         "balanceAvg":{
         *             "avg":{
         *                 "field":"balance"
         *             }
         *         }
         *     }
         * }
         */

        // 执行检索
        SearchResponse searchResponse = client.search(searchRequest, ElasticSearchConfig.COMMON_OPTIONS);
        System.out.println("检索结果" + searchResponse.toString());
        /**
         * 返回的结果数据：
         * {
         *     "took":4,
         *     "timed_out":false,
         *     "_shards":{
         *         "total":1,
         *         "successful":1,
         *         "skipped":0,
         *         "failed":0
         *     },
         *     "hits":{
         *         "total":{
         *             "value":4,
         *             "relation":"eq"
         *         },
         *         "max_score":5.4032025,
         *         "hits":[
         *             {
         *                 "_index":"bank",
         *                 "_type":"account",
         *                 "_id":"970",
         *                 "_score":5.4032025,
         *                 "_source":{
         *                     "account_number":970,
         *                     "balance":19648,
         *                     "firstname":"Forbes",
         *                     "lastname":"Wallace",
         *                     "age":28,
         *                     "gender":"M",
         *                     "address":"990 Mill Road",
         *                     "employer":"Pheast",
         *                     "email":"forbeswallace@pheast.com",
         *                     "city":"Lopezo",
         *                     "state":"AK"
         *                 }
         *             },
         *             {
         *                 "_index":"bank",
         *                 "_type":"account",
         *                 "_id":"136",
         *                 "_score":5.4032025,
         *                 "_source":{
         *                     "account_number":136,
         *                     "balance":45801,
         *                     "firstname":"Winnie",
         *                     "lastname":"Holland",
         *                     "age":38,
         *                     "gender":"M",
         *                     "address":"198 Mill Lane",
         *                     "employer":"Neteria",
         *                     "email":"winnieholland@neteria.com",
         *                     "city":"Urie",
         *                     "state":"IL"
         *                 }
         *             },
         *             {
         *                 "_index":"bank",
         *                 "_type":"account",
         *                 "_id":"345",
         *                 "_score":5.4032025,
         *                 "_source":{
         *                     "account_number":345,
         *                     "balance":9812,
         *                     "firstname":"Parker",
         *                     "lastname":"Hines",
         *                     "age":38,
         *                     "gender":"M",
         *                     "address":"715 Mill Avenue",
         *                     "employer":"Baluba",
         *                     "email":"parkerhines@baluba.com",
         *                     "city":"Blackgum",
         *                     "state":"KY"
         *                 }
         *             },
         *             {
         *                 "_index":"bank",
         *                 "_type":"account",
         *                 "_id":"472",
         *                 "_score":5.4032025,
         *                 "_source":{
         *                     "account_number":472,
         *                     "balance":25571,
         *                     "firstname":"Lee",
         *                     "lastname":"Long",
         *                     "age":32,
         *                     "gender":"F",
         *                     "address":"288 Mill Street",
         *                     "employer":"Comverges",
         *                     "email":"leelong@comverges.com",
         *                     "city":"Movico",
         *                     "state":"MT"
         *                 }
         *             }
         *         ]
         *     },
         *     "aggregations":{
         *         "lterms#ageAgg":{
         *             "doc_count_error_upper_bound":0,
         *             "sum_other_doc_count":0,
         *             "buckets":[
         *                 {
         *                     "key":38,
         *                     "doc_count":2
         *                 },
         *                 {
         *                     "key":28,
         *                     "doc_count":1
         *                 },
         *                 {
         *                     "key":32,
         *                     "doc_count":1
         *                 }
         *             ]
         *         },
         *         "avg#ageAvg":{
         *             "value":34
         *         },
         *         "avg#balanceAvg":{
         *             "value":25208
         *         }
         *     }
         * }
         */
//        Map map = JSON.parseObject(search.toString(), Map.class);
        /**对检索结果search进行分析;（分析结果）**/
        // 1、获取所有查询到的数据
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            String sourceAsString = searchHit.getSourceAsString();
            Account account = JSON.parseObject(sourceAsString, Account.class);
            System.out.println("account" + account.toString());
        }

        // 2、获取检索的分析信息（聚合等）
        Aggregations aggregations = searchResponse.getAggregations();
        Terms ageAgg = aggregations.get("ageAgg");
        for (Terms.Bucket bucket : ageAgg.getBuckets()) {
            String keyAsString = bucket.getKeyAsString();
            System.out.println("年龄" + keyAsString +"====> " + bucket.getDocCount());
        }

        Avg ageAvg = aggregations.get("ageAvg");
        double value = ageAvg.getValue();
        System.out.println("平均年龄" + value);

        Avg balanceAvg = aggregations.get("balanceAvg");
        double value1 = balanceAvg.getValue();
        System.out.println("平均薪资" + value1);

    }


    /**
     * 测试存储数据到es中 更新也可以
     *
     * @Author JnYou
     */
    @Test
    void indexData() throws IOException {
        // 创建索引
        IndexRequest indexRequest = new IndexRequest("users");
        // 设置ID或者不设置会自动生成
        indexRequest.id("1");

        // 设置真正的数据集
//        indexRequest.source("userName","zs","age",18,"gender","Male");
        // 通过对象存储
        User user = new User();
        user.setUserName("jnyou");
        user.setGender("Male");
        user.setAge(18);
        String jsonString = JSON.toJSONString(user);
        indexRequest.source(jsonString, XContentType.JSON);

        // 执行保存操作
        IndexResponse index = client.index(indexRequest, ElasticSearchConfig.COMMON_OPTIONS);

        // 提取响应数据
        System.out.println(index);

    }

    @Data
    static
    class User {
        private String userName;
        private String gender;
        private Integer age;
    }


    /**
     * Auto-generated: 2020-12-06 16:26:48
     *
     * @author json.cn (i@json.cn)
     * @website http://www.json.cn/java2pojo/
     */
    @ToString
    @Data
    static class Account {

        private int account_number;
        private int balance;
        private String firstname;
        private String lastname;
        private int age;
        private String gender;
        private String address;
        private String employer;
        private String email;
        private String city;
        private String state;
    }


}

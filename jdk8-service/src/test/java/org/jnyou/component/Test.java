package org.jnyou.component;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.jnyou.entity.Goods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分类名称
 *
 * @ClassName Test
 * @Description: 测试parallelStream并行流与普通遍历得速度
 * @Author: jnyou
 * @create 2020/08/09
 * @module 智慧园区
 **/
public class Test {

    public static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws InterruptedException {
        test1();
//        test2();
    }

    public static void test1() throws InterruptedException {
        // 普通循环  6S
        List<Goods> appleList = initAppleList();

        Date begin = new Date();
        for (Goods good : appleList) {
            good.setPrice(5.0 * good.getWeight() / 1000);
            Thread.sleep(1000);
        }
        Date end = new Date();
        System.out.println("商品数量：{"+ appleList.size() +"}个, 耗时：{}s"  + (end.getTime() - begin.getTime()) /1000);
        logger.info("商品数量：{}个, 耗时：{}s", appleList.size(), (end.getTime() - begin.getTime()) /1000);
        logger.info("商品数量：{6}个, 耗时：{}s6");
    }

    public static void test2(){
        List<Goods> appleList = initAppleList();
        Date begin = new Date();
        appleList.parallelStream()
                .forEach(apple ->
                        {
                            apple.setPrice(5.0 * apple.getWeight() / 1000);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                );
        Date end = new Date();
        System.out.println("商品数量：{"+ appleList.size() +"}个, 耗时：{}s"  + (end.getTime() - begin.getTime()) /1000);
        logger.info("苹果数量：{}个, 耗时：{}s", appleList.size(), (end.getTime() - begin.getTime()) /1000);
        logger.info("商品数量：{6}个, 耗时：{}s3");
    }


    /***
     * 获取数据
     * @return
     * @Author jnyou
     * @Date 2020/8/9
     */
    public static List<Goods> initAppleList(){
        List<Goods> list = new ArrayList<>();
        for (int i = 0; i < 6; i ++){
            Goods goods = new Goods();
            goods.setPrice(10 + i);
            goods.setWeight(2 + i);
            list.add(goods);
        }
        return list;
    }

}
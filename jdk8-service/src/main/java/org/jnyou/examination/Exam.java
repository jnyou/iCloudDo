package org.jnyou.examination;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.jnyou.entity.Goods;
import org.jnyou.mapper.ExamMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 分类名称
 *
 * @ClassName Exam
 * @Description: Mp与lambda的混合操作
 * @Author: jnyou
 **/
public class Exam {

    @Autowired
    private ExamMapper examMapper;

    public static void main(String[] args) {

    }


    public Map<String, Object> listByItem(Goods goods) {
        List<Goods> goodsList = examMapper.selectList(
                Wrappers.<Goods>lambdaQuery()
                        .eq(StringUtils.isNotBlank(goods.getGoodName()), Goods::getGoodName, goods.getGoodName())
                        .notLike(!org.springframework.util.StringUtils.isEmpty(goods.getCreateTime()), Goods::getCreateTime, goods.getCreateTime())
        );

        return null;
    }

}
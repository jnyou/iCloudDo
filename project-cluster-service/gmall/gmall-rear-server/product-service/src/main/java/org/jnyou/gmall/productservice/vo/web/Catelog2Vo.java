package org.jnyou.gmall.productservice.vo.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 *
 * @className Catelog2Vp
 * @author: JnYou xiaojian19970910@gmail.com
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Catelog2Vo {

    // 一级父分类
    private String catalog1Id;
    // 三级子分类
    private List<Catelog3Vo> catalog3List;
    // 二级分类ID
    private String id;
    // 二级分类名称
    private String name;

    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Catelog3Vo {
        private String catalog2Id;
        private String id;
        private String name;
    }

}
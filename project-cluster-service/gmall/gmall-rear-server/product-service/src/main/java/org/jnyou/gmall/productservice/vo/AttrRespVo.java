package org.jnyou.gmall.productservice.vo;

import lombok.Data;
import org.jnyou.gmall.productservice.entity.AttrEntity;

/**
 * 分类名称
 *
 * @ClassName AttrRespVo
 * @Description:
 * @Author: jnyou
 **/
@Data
public class AttrRespVo extends AttrVo {

    /**
     * 所属分类名字
     */
    private String catelogName;
    /**
     * 所属分组名字
     */
    private String groupName;

}
package org.jnyou.gmall.productservice.vo;

import lombok.Data;

import java.util.List;

@Data
public class SpuItemAttrGroupVo {
    private String groupNmae;
    private List<Attr> attrs;
}
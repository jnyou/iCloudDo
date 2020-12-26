package org.jnyou.gmall.storageservice.vo;

import lombok.Data;

import java.util.List;

@Data
public class MergeVo {

   private Long purchaseId; //采购单ID
   private List<Long> items;//[1,2,3,4] //采购需求项ID
}

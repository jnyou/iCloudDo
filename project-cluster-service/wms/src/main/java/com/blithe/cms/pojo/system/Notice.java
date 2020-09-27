package com.blithe.cms.pojo.system;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.blithe.cms.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author: youjiannan
 * @Description: 系统公告模块
 * @Date: 2020/3/19
 * @Param:
 * @Return:
 **/
@Data
@TableName("sys_notice")
public class Notice extends BaseVO {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private Date createtime;

    private String opername;


}

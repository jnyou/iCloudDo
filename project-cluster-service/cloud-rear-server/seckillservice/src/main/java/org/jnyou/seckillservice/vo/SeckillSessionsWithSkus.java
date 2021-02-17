package org.jnyou.seckillservice.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * SeckillSessionsWithSkus
 * 秒杀的场次信息
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
public class SeckillSessionsWithSkus {

    /**
     * id
     */
    private Long id;
    /**
     * 场次名称
     */
    private String name;
    /**
     * 每日开始时间
     */
    private Date startTime;
    /**
     * 每日结束时间
     */
    private Date endTime;
    /**
     * 启用状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 秒杀活动关联的商品
     */
    private List<SeckillSkuVo> entities;

}
package entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分类名称
 *
 * @ClassName Member
 * @Description: 用户实体
 * @Author: jnyou
 **/
@Data
@Accessors(chain = true)
public class Member {

    private Long id;

    //姓名
    private String name;

    //年龄
    private int age;

    //工号
    private String jobNumber;

    //性别
    private String sex;

    //入职日期
    private Date entryDate;

    //家庭成员数量
    private BigDecimal familyMemberQuantity;

}
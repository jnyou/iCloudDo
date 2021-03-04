package io.jnyou.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 代码千万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * RolePrivilegesParam
 *
 * @version 1.0.0
 * @author: JnYou
 **/
@Data
@ApiModel(value = "接受角色和权限参数")
public class RolePrivilegesParam {

    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    @ApiModelProperty(value = "角色包含的权限")
    private List<Long> privilegeIds = Collections.emptyList();

}
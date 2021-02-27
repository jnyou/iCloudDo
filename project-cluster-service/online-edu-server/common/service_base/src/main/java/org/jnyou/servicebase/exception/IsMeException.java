package org.jnyou.servicebase.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName yjn
 * @Description:
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsMeException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "异常信息")
    private String msg;

}

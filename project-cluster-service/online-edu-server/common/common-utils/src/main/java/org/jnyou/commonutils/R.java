package org.jnyou.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName yjn
 * @Description: 统一返回结果对象
 * @Author: 夏小颜
 * @Version: 1.0
 **/
@Data
public class R{

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    /**
     * 构造方法私有化，防止别人直接new对象，只能调用相应静态方法和链式编程操作
     *
     */
    private R(){}

    /**
     * 成功静态方法
     */
    public static R ok(){
        R r = new R();
        r.setCode(Constast.SUCCESS);
        r.setSuccess(true);
        r.setMessage("成功");
        return r;
    }

    /**
     * 失败静态方法
     */
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(Constast.ERROR);
        r.setMessage("失败");
        return r;
    }

    /**
     * 以下主要用于链式编程  返回this指的当前对象可以继续执行
     *
     */

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R put(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
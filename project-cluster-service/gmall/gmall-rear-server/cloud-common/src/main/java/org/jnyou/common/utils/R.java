package org.jnyou.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.http.HttpStatus;
import org.apache.poi.ss.formula.functions.T;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

//	因为R是hashmap，所以写的所有私有属性都没用了，只能存key value了
//	private T data;  // 返回T泛型，直接让远程调用的返回值为需要查询的集合或者对象，比如直接返回List<Product> ； 解决远程调用后获取值的各项操作
//	public T getData(){
//		return data;
//	}
//	public void setData(T data){
//		this.data = data;
//	}

	public R() {
		put("code", 0);
		put("msg", "success");
	}

	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}

	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	public  Integer getCode() {
		return (Integer) this.get("code");
	}

	public R setData(Object data){
		put("data",data);
		return this;
	}

	//利用fastjson进行逆转，
	public <T> T getData(TypeReference<T> typeReference){
		//默认是map
		Object data = get("data");
		String s = JSON.toJSONString(data);
		T t = JSON.parseObject(s, typeReference);
		return t;
	}

	//利用fastjson进行逆转，
	public <T> T getData(String key,TypeReference<T> typeReference){
		//默认是map
		Object data = get(key);
		String s = JSON.toJSONString(data);
		T t = JSON.parseObject(s, typeReference);
		return t;
	}

}

package io.jnyou.core.vo;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import java.util.List;

@Data
public class AssetVo {

	/*
	 * 资产的公共属性
	 * 
	 */
	private String fullName;
	private String valueStr;
	private String kindStr;
	private Integer siteId;

	@NumberFormat(style= Style.NUMBER)
	private Integer id;//资产编号
	@NumberFormat(style= Style.NUMBER)
	private Integer parentId;//父资产编号
	private Integer parent;
	private String typeName;
	private String parentCaption;
	private String name;//资产的名称
	private String caption;//显示名称
	private String memo;//描述
	private String state;//状态
	private String error;//错误信息
	@NumberFormat(style= Style.NUMBER)
	private Integer enabled;
	private String typeCaption;
	private String kind;//分类
	private Integer removed;//是否删除
//	private List<AssetProperty> valueList ;//asset的属性
	/*
	 *moniter的属性 
	 */
	private String value;//监测器     值
	private String serviceCaption;//服务    名称
	private String time_interval;//监测器   间隔时间
	private String save_interval;//监测器   间隔时间
	private String unit;//单位
	private String time_unit;
	@NumberFormat(style= Style.NUMBER)
	private Integer refresh_delay;
	private String warn_cond;// 监测器    警告表达式
	private String transform;//结果变换
	@NumberFormat(style= Style.NUMBER)
	private Integer source;//服务id
	@NumberFormat(style= Style.NUMBER)
	private Integer catalogId;
	private String minValue;//监测器     值
	private String maxValue;
	/*
	 * 设备的属性
	 */
	private String vendor;//设备 生产厂家
	private String purchase_date;//设备   采购日期
	private String warranty_date;// 设备 保修日期
	private String specification;//规格型号
	private String models;//资产类型
	private String increse_way;//增加方式
	private String international_code;//国际编码
	private String detail_config;//详情配置
	private String production_date;//制造日期
	@NumberFormat(style= Style.NUMBER)
	private Integer departId;
	
	
	private String using_state;//使用状态
	private String device_type;//设备类型
	@NumberFormat(style= Style.NUMBER)
	private Integer location;//存放地点 spaceId
	private String spaceCaption;
	private String user;
	@NumberFormat(style= Style.NUMBER)
	private Integer userId;
	@NumberFormat(style= Style.NUMBER)
	private Integer quantity;//数量
	private String deviceUnit;//单位
	@NumberFormat(style= Style.CURRENCY)
	private Double price;//单价
	@NumberFormat(style= Style.CURRENCY)
	private Double amount;//金额
	private String enabing_date;//启用时间
	@NumberFormat(style= Style.NUMBER)
	private Integer maintenance_interval;//维修间隔月
	@NumberFormat(style= Style.NUMBER)
	private Integer original_value;//原值
	@NumberFormat(style= Style.NUMBER)
	private Integer use_year;//使用年限
	@NumberFormat(style= Style.PERCENT)
	private Double  salvage;//残值率
	@NumberFormat(style= Style.CURRENCY)
	private Double salvage_value;//残值
	private String depreciation_method;//折旧方法
	private String maintenance_time;//维修时间
	private String maintenance_people;//维修人
	private Integer scrap;//是否报废标志 0-报废 非0－没报废
	private Integer is_using;//是否使用标志 0-没使用 非0－使用
	private String qrcode;
	
	//摄像机的属性
	private String username;
	private String password;
	private String host;
	@NumberFormat(style= Style.NUMBER)
	private int port;
	private String rtspUrlPattern;
	private String huifangurl;
	@NumberFormat(style= Style.NUMBER)
	private int spaceId;
	
	private String monitorType;

	private Integer moduleId;

}

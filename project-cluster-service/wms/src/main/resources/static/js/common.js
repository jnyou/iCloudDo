$(function () {

    //修改自带的序列化功能
    jQuery.prototype.serializeObject=function(){
        var a,o,h,i,e;
        a=this.serializeArray();
        o={};
        h=o.hasOwnProperty;
        for(i=0;i<a.length;i++){
            e=a[i];
            if(!h.call(o,e.name)){
                if(e.value){
                    o[e.name]=e.value;
                }else{
                    o[e.name]="";
                }
            }
        }
        return o;
    };
    /**
     * 去空的序列化
     * @returns {{}|*}
     */
    $.fn.serializeNullObject = function() {
        var a,o,h,i,e;
        a=this.serializeArray();
        o={};
        h=o.hasOwnProperty;
        for(i=0;i<a.length;i++){
            e=a[i];
            if(!h.call(o,e.name)){
                if(e.value){
                    o[e.name]=e.value;
                }
            }
        }
        return o;
    };
    /**
     * 带列表序列化
     * @returns {{}}
     */
    $.fn.serializeNestedObject = function() {
        var json = {};
        var arrObj = this.serializeArray();
        //alert(JSON.stringify(arrObj));
        $.each(arrObj, function() {
            // 对重复的name属性，会将对应的众多值存储成json数组
            if (json[this.name]) {
                if (!json[this.name].push) {
                    json[this.name] = [ json[this.name] ];
                }
                json[this.name].push(this.value || '');
            } else {
                // 有嵌套的属性，用'.'分隔的
                if (this.name.indexOf('.') > -1) {
                    var pos = this.name.indexOf('.');
                    var key =  this.name.substring(0, pos);
                    // 判断此key是否已存在json数据中，不存在则新建一个对象出来
                    if(!existKeyInJSON(key, json)){
                        json[key] = {};
                    }
                    var subKey = this.name.substring(pos + 1);
                    json[key][subKey] = this.value || '';
                }
                // 普通属性
                else{
                    json[this.name] = this.value || '';
                }

            }
        });

        // 处理那些值应该属于数组的元素，即带'[number]'的key-value对
        var resultJson = {};
        for(var key in json){
            // 数组元素
            if(key.indexOf('[') > -1){
                var pos = key.indexOf('[');
                var realKey =  key.substring(0, pos);
                // 判断此key是否已存在json数据中，不存在则新建一个数组出来
                if(!existKeyInJSON(realKey, resultJson)){
                    resultJson[realKey] = [];
                }
                resultJson[realKey].push(json[key]);

            }
            else{ // 单元素
                resultJson[key] = json[key];
            }
        }
        return resultJson;
    };
    //禁止所有input回车键刷新
    $('input').on('keydown', function (event) {
        if (event.keyCode == 13) {


            return false
        }
    });
});
function existKeyInJSON(key, json){
    if(key == null || key == '' || $.isEmptyObject(json)){
        return false;
    }
    var exist = false;
    for(var k in json){
        if(key === k){
            exist = true;
        }
    }
    return exist;
}

/**
 * **********************************Begin
 * 进行加减乘除调用的方法*******************************************
 */
// 除法函数，用来得到精确的除法结果
// 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
// 调用：accDiv(arg1,arg2)
// 返回值：arg1除以arg2的精确结果
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
};

// 给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function(arg) {
	return accDiv(this, arg);
};

// 乘法函数，用来得到精确的乘法结果
// 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
// 调用：accMul(arg1,arg2)
// 返回值：arg1乘以arg2的精确结果
function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length;
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", ""))
			/ Math.pow(10, m);
};

// 给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function(arg) {
	return accMul(arg, this);
};

// 加法函数，用来得到精确的加法结果
// 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
// 调用：accAdd(arg1,arg2)
// 返回值：arg1加上arg2的精确结果
function accAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return Math.round(arg1 * m + arg2 * m) / m;
}

// 给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function(arg) {
	return accAdd(arg, this);
};

//减法函数，用来得到精确的减法结果
//说明：javascript的减法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的减法结果。
//调用：accSubtr(arg1,arg2)
//返回值：arg1减去arg2的精确结果
function accSubtr(arg1,arg2){
    var r1,r2,m,n;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
//动态控制精度长度
    n=(r1>=r2)?r1:r2;
    return ((arg1*m-arg2*m)/m).toFixed(n);
}
//给Number类型增加一个subtr 方法，调用起来更加方便。
Number.prototype.subtr = function (arg){
    return accSubtr(arg,this);
}


/**
 * ************************************end
 * 进行加减乘除调用的方法*******************************************
 */

/**
 * 判断字符串是以什么开头
 */
String.prototype.startWith = function(str) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	if (this.substr(0, str.length) == str)
		return true;
	else
		return false;
	return true;
};

/**
 * 格式化字符串（将字符串左补位至固定长度，如果超过固定长度，不做处理，原样返回）
 * 
 * @param str 要格式化的字符串
 * @param len 长度
 * @param c  补位字符
 * @returns {String}
 */
function formatStr(str, len, c) {
	var l = str.length;
	var newStr = '';
	if (parseInt(l) < parseInt(len)) {
		for ( var i = 0; i < (parseInt(len) - parseInt(l)); i++) {
			newStr += c;
		}
	}
	return newStr + str;
};
/**
 * 金额转换成大写
 * @param num
 * @returns {*}
 */
function amountChange ( money ) {
    //汉字的数字
    var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');
    //基本单位
    var cnIntRadice = new Array('', '拾', '佰', '仟');
    //对应整数部分扩展单位
    var cnIntUnits = new Array('', '万', '亿', '兆');
    //对应小数部分单位
    var cnDecUnits = new Array('角', '分', '毫', '厘');
    //整数金额时后面跟的字符
    var cnInteger = '整';
    //整型完以后的单位
    var cnIntLast = '元';
    //最大处理的数字
    var maxNum = 999999999999999.9999;
    //金额整数部分
    var integerNum;
    //金额小数部分
    var decimalNum;
    //输出的中文金额字符串
    var chineseStr = '';
    //分离金额后用的数组，预定义
    var parts;
    if (money == '') { return ''; }
    money = parseFloat(money);
    if (money >= maxNum) {
        //超出最大处理数字
        return '超出范围';
    }
    if (money == 0) {
        chineseStr = cnNums[0] + cnIntLast + cnInteger;
        return chineseStr;
    }
    //转换为字符串
    money = money.toString();
    if (money.indexOf('.') == -1) {
        integerNum = money;
        decimalNum = '';
    } else {
        parts = money.split('.');
        integerNum = parts[0];
        decimalNum = parts[1].substr(0, 4);
    }
    //获取整型部分转换
    if (parseInt(integerNum, 10) > 0) {
        var zeroCount = 0;
        var IntLen = integerNum.length;
        for (var i = 0; i < IntLen; i++) {
            var n = integerNum.substr(i, 1);
            var p = IntLen - i - 1;
            var q = p / 4;
            var m = p % 4;
            if (n == '0') {
                zeroCount++;
            } else {
                if (zeroCount > 0) {
                    chineseStr += cnNums[0];
                }
                //归零
                zeroCount = 0;
                chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
            }
            if (m == 0 && zeroCount < 4) {
                chineseStr += cnIntUnits[q];
            }
        }
        chineseStr += cnIntLast;
    }
    //小数部分
    if (decimalNum != '') {
        var decLen = decimalNum.length;
        for (var i = 0; i < decLen; i++) {
            var n = decimalNum.substr(i, 1);
            if (n != '0') {
                chineseStr += cnNums[Number(n)] + cnDecUnits[i];
            }
        }
    }
    if (chineseStr == '') {
        chineseStr += cnNums[0] + cnIntLast + cnInteger;
    } else if (decimalNum == '') {
        chineseStr += cnInteger;
    }
    return chineseStr;
}


/**
 * 只允许输入两位小数点的数字
 * @param obj
 */
function clearNoNumFinterestRate(obj, num3){
    var v = parseFloat(obj.value, 3);
    if(v > 100){
        obj.value = 100;
    }
    //先把非数字的都替换掉，除了数字和.
    obj.value = obj.value.replace(/[^\d.]/g,"");

    //保证只有出现一个.而没有多个.
    obj.value = obj.value.replace(/\.{2,}/g,".");

    //必须保证第一个为数字而不是.
    obj.value = obj.value.replace(/^\./g,"");

    //保证.只出现一次，而不能出现两次以上
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");

    if(num3 == 4) {
        //只能输入3位小数
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d\d\d).*$/,'$1$2.$3');
    } else if(num3 == 3) {
        //只能输入3位小数
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,'$1$2.$3');
    } else {
        //只能输入两个小数
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
    }
}




/**
 * 只允许输入两位小数点的数字
 * @param obj
 */
function clearNoNum(obj, num3){
    var v = parseFloat(obj.value, 10);
    if(v > 10000000000){
        obj.value = 10000000000;
    }
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g,"");

	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g,".");

	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g,"");

	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");

    if(num3 == 4) {
        //只能输入3位小数
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d\d\d).*$/,'$1$2.$3');
    } else if(num3 == 3) {
        //只能输入3位小数
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,'$1$2.$3');
    } else {
        //只能输入两个小数
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
    }
}

/**
 * 数字格式化 例如小数点位数后面多位不用逗号分割 0.00000
 * @param s
 * @param n
 * @returns {String}
 */
function fmoney(s, n) {
	if(s == undefined){
		s=0;
	}
    var v = parseFloat(s, 10);
    if(v > 10000000000){
        s = 10000000000;
    }
    /*
     * 参数说明：
     * s：要格式化的数字
     * n：保留几位小数
     * */
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(),
        r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
}

function fmoneySum(s, n) {
    if(s == undefined){
        s=0;
    }
    // var v = parseFloat(s, 10);
    // if(v > 10000000000){
    //     s = 10000000000;
    // }
    /*
     * 参数说明：
     * s：要格式化的数字
     * n：保留几位小数
     * */
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(),
        r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
}

function fmoneyFormat(s, n) {
    if(s == undefined){
        s=0;
    }
    /*
     * 参数说明：
     * s：要格式化的数字
     * n：保留几位小数
     * */
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(),
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("");
}
/**
 * 金额格式化
 * @param num
 * 
 * */
function thousands(num){
	return thousandstwo(num,2);
};
/**
 * 金额格式化自定义小数点位数
 * @param num
 * @param xshuwei  想要设置的小数点位数
 * 
 * */
function thousandstwo(num,xshuwei){
    if(!num){
        var s="0";
        if(xshuwei&&xshuwei>0){
            s=s+".";
            for(var i=0;i<xshuwei;i++){
                s=s+"0";
            }
        }
        return s;
    }
    return (parseFloat(num).toFixed(parseInt(xshuwei)) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
};

/**
 * 清除ID内的input文本框，复位select下拉菜单
 * @param idstr  需要清除的控件的ID ，示例：#wiseborrowsearch
 */
function clearSereach(idstr){
    // 清空文本框
    $(idstr).find('input').each(function(){
        $(this).val("");
    });
    // 复位下拉菜单
    $(idstr).find('select').each(function(){
        $(this).find('option:first-child').attr('selected',"selected");
    });
}

/*
格式化数据，小数部分不做处理，对整数部分进行千分位格式化的处理，如果有符号，正常保留
*/
function formatCurrency(num) {
  if(num)
  {
      //将num中的$,去掉，将num变成一个纯粹的数据格式字符串
      num = num.toString().replace(/\$|\,/g,'');
      //如果num不是数字，则将num置0，并返回
      if(''==num || isNaN(num)){return 'Not a Number ! ';}
      //如果num是负数，则获取她的符号
      var sign = num.indexOf("-")> 0 ? '-' : '';
      //如果存在小数点，则获取数字的小数部分
      var cents = num.indexOf(".")> 0 ? num.substr(num.indexOf(".")) : '';
      cents = cents.length>1 ? cents : '' ;//注意：这里如果是使用change方法不断的调用，小数是输入不了的
      //获取数字的整数数部分
      num = num.indexOf(".")>0 ? num.substring(0,(num.indexOf("."))) : num ;
      //如果没有小数点，整数部分不能以0开头
      if('' == cents){ if(num.length>1 && '0' == num.substr(0,1)){return 'Not a Number ! ';}}
      //如果有小数点，且整数的部分的长度大于1，则整数部分不能以0开头
      else{if(num.length>1 && '0' == num.substr(0,1)){return 'Not a Number ! ';}}
      //针对整数部分进行格式化处理，这是此方法的核心，也是稍难理解的一个地方，逆向的来思考或者采用简单的事例来实现就容易多了
      /*
        也可以这样想象，现在有一串数字字符串在你面前，如果让你给他家千分位的逗号的话，你是怎么来思考和操作的?
        字符串长度为0/1/2/3时都不用添加
        字符串长度大于3的时候，从右往左数，有三位字符就加一个逗号，然后继续往前数，直到不到往前数少于三位字符为止
       */
      for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
      {
          num = num.substring(0,num.length-(4*i+3))+','+num.substring(num.length-(4*i+3));
      }
      //将数据（符号、整数部分、小数部分）整体组合返回
      return (sign + num + cents);    
  }

}
/**
 * 省市区联动 select
 * select示例：
 *    <select  name="province" id="sellergoodsprovince"></select>&nbsp;
 *    <select  name="city"  id="sellergoodscity" ></select>&nbsp;
 *    <select name="country"  id="sellergoodscounty"></select>
 * provinceId 省标签ID; 如#sellergoodsprovince
 * cityId 市标签ID； 如#sellergoodsprovince
 * areaId 区域标签ID；如#sellergoodscounty
 */
function cityLinkage(provinceId, cityId, areaId){
    $(provinceId).empty();
    $(cityId).empty();
    $(areaId).empty();
    $(provinceId).append('<option id="choosePro" value="" selected = selected >请选择您所在省份</option>');
    $(cityId).append("<option id='chooseCity' value='' selected = selected>请选择您所在城市</option>");
    $(areaId).append("<option id='chooseCounty' value='' selected = selected>请选择您所在区/县</option>");
    //省信息初始化
    $.getJSON(ctx+"/statics/js/prcity.json",function (data) {
        allcityjson = data;
        $.each(allcityjson, function(index, value){
            var option = "<option index='" + index + "' value='" + value.name + "'>" + value.name+ "</option>";
            $(provinceId).append(option);
        });


    });
    //省变动，联动城市变动
    $(provinceId).change(function () {
        index = $(provinceId).find("option:selected").attr("index");
        if(undefined != allcityjson[index].city) {
            cityjson = allcityjson[index].city;
            $(cityId).empty();
            $(cityId).append("<option id='chooseCity' value=''>请选择您所在城市</option>");
            $(areaId).empty();
            $(areaId).append("<option id='chooseCounty' value=''>请选择您所在区/县</option>");
            if(cityjson.length>0) {
                $.each(cityjson, function(index, value){
                    var option = "<option index='" + index + "' value='" + value.name + "'>" + value.name+ "</option>";
                    $(cityId).append(option);
                });
            }
        }
    });
    //市变动，联动区域变动
    $(cityId).change(function () {
        index = $(cityId).find("option:selected").attr("index");
        if(undefined != cityjson[index].area) {
            areajson = cityjson[index].area;
            $(areaId).empty();
            $(areaId).append("<option id='chooseCounty' value=''>请选择您所在区/县</option>");
            if(areajson.length>0) {
                $.each(areajson, function(index, name){
                    var option = "<option index='" + index + "' value='" + name + "'>" + name+ "</option>";
                    $(areaId).append(option);
                });
            }
        }
    });


};

/**
 * 通过省，获取市区值
 * provinceId 省标签ID; 如#sellergoodsprovince
 * cityId 市标签ID； 如#sellergoodsprovince
 * areaId 区域标签ID；如#sellergoodscounty
 * province
 * city
 * county
 */
function loadcityLinkage(provinceId, cityId, areaId, province, city, county){
    //省信息初始化
    $.getJSON(ctx+"/statics/js/prcity.json",function (data) {
        allcityjson = data;
        var provinceindex = null;
        var cityindex = null;
        $(provinceId).empty();
        $(provinceId).append('<option id="choosePro" value＝"" disabled secleted>请选择您所在省份</option>');
        $(cityId).empty();
        $(cityId).append("<option id='chooseCity' value=''>请选择您所在城市</option>");
        $(areaId).empty();
        $(areaId).append("<option id='chooseCounty' value=''>请选择您所在区/县</option>");
        $.each(allcityjson, function(index, value){
            var selected = "";
            if(value.name == province) {
                provinceindex = index;
                var selected = "selected = selected";
            }
            var option = "<option "+ selected +" index='" + index + "' value='" + value.name + "'>" + value.name+ "</option>";
            $(provinceId).append(option);
        });

        if(provinceindex!=null) {
            cityjson = allcityjson[provinceindex].city;
            if(cityjson.length>0) {
                $.each(cityjson, function(index, value){
                    var selected = "";
                    if(value.name == city) {
                        cityindex = index;
                        var selected = "selected = selected";
                    }
                    var option = "<option "+ selected +" index='" + index + "' value='" + value.name + "'>" + value.name+ "</option>";
                    $(cityId).append(option);

                });
            }
        }

        if(cityindex!=null) {
            areajson = cityjson[cityindex].area;
            if(areajson.length>0) {
                $.each(areajson, function(index, name){
                    var selected = "";
                    if(name == county) {
                        var selected = "selected = selected";
                    }
                    var option = "<option "+ selected +" index='" + index + "' value='" + name + "'>" + name+ "</option>";
                    $(areaId).append(option);
                });
            }
        }
    });
};

/**
 * yyyy-mm-dd 转换为 yyyymmdd
 * @returns {String}
 */
function getyyyyMMdd(){
    var d = new Date();
    var curr_date = d.getDate();
    var curr_month = d.getMonth() + 1; 
    var curr_year = d.getFullYear();
    String(curr_month).length < 2 ? (curr_month = "0" + curr_month): curr_month;
    String(curr_date).length < 2 ? (curr_date = "0" + curr_date): curr_date;
    var yyyyMMdd = curr_year + "" + curr_month +""+ curr_date;
    return yyyyMMdd;
} 

/**
 * xxxx-xx-xx 年月日格式化
 */
function myformatter(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
        + (d < 10 ? ('0' + d) : d);
}

function formatDate(date){ //设置时间转换格式

    var y = date.getFullYear();  //获取年

    var m = date.getMonth() + 1;  //获取月

    m = m < 10 ? '0' + m : m;  //判断月是否大于10

    var d = date.getDate();  //获取日

    d = d < 10 ? ('0' + d) : d;  //判断日期是否大10

    return y + '-' + m + '-' + d;  //返回时间格式

}
/**
 * xxxx-xx-xx hh:mm:ss 年月日时分秒格式化
 * @param date
 * @returns {string}
 */
function getCurrentDate(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    var h = date.getHours();
    var min = date.getMinutes();
    var s = date.getSeconds();
    var str=y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(s<10?('0'+s):s);
    return str;
}


/**
 * 获取两个日期的天数间隔
 * 不区分起始和结束
 */

function DateDiff(sDate1, sDate2) {  //sDate1和sDate2是yyyy-MM-dd格式
    var aDate, oDate1, oDate2, iDays;
    aDate = sDate1.split("-");
    // 此写法不兼容firefox, firefox浏览器返回如下结果
    // new Date("04" + '-' + "25" + '-' + "2019")
    // 返回Invalid Date
    oDate1 = new Date(aDate[0] + '-' + aDate[1] + '-' + aDate[2]);  //转换为yyyy-MM-dd格式
    aDate = sDate2.split("-");
    oDate2 = new Date(aDate[0] + '-' + aDate[1] + '-' + aDate[2]);
    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
    return iDays;  //返回相差天数
}

//判断结束日期是否大于开始日期
function compareDate(starttime,endtime) {
    var start = new Date(starttime.replace("-", "/").replace("-", "/"));
    var end = new Date(endtime.replace("-", "/").replace("-", "/"));
    return end < start;
}

function formateDate(num,type) {
    //Fri Oct 31 18:00:00 UTC+0800 2008
    num = num + ""; //给字符串后就一个空格
    var date = "";
    var month = new Array();
    month["Jan"] = "01"; month["Feb"] = "02"; month["Mar"] = "03"; month["Apr"] = "04";

    month["May"] = "05"; month["Jun"] = "06"; month["Jul"] = "07"; month["Aug"] = "08";

    month["Sep"] = "09"; month["Oct"] = "10"; month["Nov"] = "11"; month["Dec"] = "12";
    var week = new Array();
    week["Mon"] = "一"; week["Tue"] = "二"; week["Wed"] = "三"; week["Thu"] = "四";

    week["Fri"] = "五"; week["Sat"] = "六"; week["Sun"] = "日";
    str = num.split(" "); //根据空格组成数组
    date = str[5] + "-"; //就是在2008的后面加一个“-”

    //通过修改这里可以得到你想要的格式
    if(type=="1"){
    	//yyyy-MM-dd
    	date = date + month[str[1]] + "-" + str[2];
    }else{
    	//yyyy-MM-dd HH:mm:ss
    	date = date + month[str[1]] + "-" + str[2] + " " + str[3];
    }
    	 

    //date=date+" 周"+week[str[0]];
    return date;
}

//通用表单提交
function downLoadCommon(options) {
    var config = $.extend(true, { method: 'get' }, options);
    var $form=$("<form>");//定义一个form表单
    $form.attr("style","display:none");
    $form.attr("target","");
    $form.attr("method",config.method); //请求类型
    $form.attr("action",config.url); //请求地址
    $("body").append($form);//将表单放置在web中
    for (var key in config.data) {
        $form.append('<input type="hidden" name="' + key + '" value="' + config.data[key] + '" />');
    }
    $form.submit();//表单提交
}

/*弹出层*/
/*
 参数解释：
 title   标题
 url     请求的url
 id      需要操作的数据id
 w       弹出层宽度（缺省调默认值）
 h       弹出层高度（缺省调默认值）
 */
function x_admin_show(title,url,w,h){
    if (title == null || title == '') {
        title=false;
    };
    if (url == null || url == '') {
        url="404.html";
    };
    if (w == null || w == '') {
        w=($(window).width()*0.9);
    };
    if (h == null || h == '') {
        h=($(window).height() - 50);
    };
    layer.open({
        type: 2,
        area: [w+'px', h +'px'],
        fix: false, //不固定
        maxmin: true,
        shadeClose: true,
        shade:0.4,
        title: title,
        content: url
    });
}

/*关闭弹出框口*/
function x_admin_close(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}

//获取当前日期的年月日格式 例:2019-05-27
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    return currentdate;
}

//将时间戳转换为日期 例如:timestampToTime(1559232000000)  "2019-05-31 0:0:0"
function timestampToTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D+h+m+s;
}

//获取当天指定时间的时间戳
function getAppointTime(time){
	var date = new Date();
	var zero = parseInt(date.getTime()/(1000 * 3600 * 24))*(1000 * 3600 * 24) + date.getTimezoneOffset() * 60000;
	var timeD = time * 60 * 60 * 1000;
	var currentTime = zero + timeD;
	return currentTime;
}

//格式化时间
function formatTime(datetime,fmt){
    if(datetime==null){
        return '';
    };
    if (parseInt(datetime)==datetime) {
        if (datetime.length==10) {
            datetime=parseInt(datetime)*1000;
        } else if(datetime.length==13) {
            datetime=parseInt(datetime);
        }
    };
    datetime=NewDate(datetime);
    var o = {
        "M+" : datetime.getMonth()+1,                 //月份
        "d+" : datetime.getDate(),                    //日
        "h+" : datetime.getHours(),                   //小时
        "m+" : datetime.getMinutes(),                 //分
        "s+" : datetime.getSeconds(),                 //秒
        "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
        "S"  : datetime.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

function NewDate(str){
    if(!str){
        return 0;
    }
    arr=str.split(" ");
    d=arr[0].split("-");
    t=arr[1].split(":");
    var date = new Date();
    date.setUTCFullYear(d[0], d[1] - 1, d[2]);
    date.setUTCHours(t[0], t[1], t[2], 0);
    return date;
}

/**
 * 获取日期：前天、昨天、今天、明天、后天
 *  "前天："+GetDateStr(-2);
	 昨天："+GetDateStr(-1);
	 今天："+GetDateStr(0);
	 明天："+GetDateStr(1);
	 后天："+GetDateStr(2);
	 大后天："+GetDateStr(3);
 * @param AddDayCount
 * @returns {String}
 */
function GetDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth()+1;//获取当前月份的日期
    var d = dd.getDate();
    return y+"-"+m+"-"+d;
}

/**
 * 发送一次请求，确认当前会话在线状态，如若超时弹出提示框，跳转到登录页面。
 * @returns
 */
function isOnline(){
    var ret = false;
    $.ajax({
        type : "post",
        url : ctx + '/online',
        async : false,
        success : function(result){
            if (result.code != 0){

            }else {
                ret = true;
            }
        }
    });
    return ret;
}

/**
 * 输出结果弹出框
 * @param msg
 * @param obj
 */
function resultLayerMsg(result) {
    if(result == null || undefined == result.code) {
        return;
    }

    if(result.code == 0 || result.code == 503) {
        return;
    }


    if(undefined !== result.msg || "" != result.msg) {
        top.layer.msg(result.msg, {icon: 2});
    }

}

//邮箱校验
function checkEmail(str){
    var re = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/;
    if (re.test(str)) {
        return true;
    } else {
        return false;
    }
}

/**
 * input 千分位，保留两位小数，下面两个方法联合
 * 使用示例如下：
 * $("#unitInfoFwarn").blur(function (value) {
 *     inputThousands("#unitInfoFwarn",this.value)
 * }
 *
 *$("#unitInfoFwarn").focus(function(){
 *       var oldMny = this.value.replace(/,/g, '');
 *       if(oldMny.indexOf(".")>0){
 *           oldMny = oldMny.replace(/0+?$/,"");//去除尾部多余的0
 *           oldMny = oldMny.replace(/[.]$/,"");//如果最后一位是.则去掉
 *       }
 *       this.value = oldMny;
 * });
 *
 * 获取值：
 *    var reg_mny = $("#mny").val().trim().replace(/,/g, '');
 * @param id
 */
function inputBlurThousands(id,val,n) {
    var obj = val;
    var mnyReg = /^([1-9][0-9]*|(([0]\.[0-9]{0,2}[1-9]\d*|[1-9][0-9]*\.\d*)))$/;//根据需求修改
    if(!(mnyReg.test(obj))){
        layer.tips("请输入正确的资金","",{time:1000});
        return;
    }
    
    obj = parseFloat(obj).toFixed(n); //obj=123456.789
    var left = obj.split(".")[0].split("").reverse();//left = ["6","5","4","3","2","1"]
    var right = obj.split(".")[1]; //right = "789"
    var total = new Array();
    for (i = 0; i < left.length; i++) {
        total.push(left[i]);
        if((i + 1) % 3 == 0 && (i + 1) != left.length){
            total.push(",");
        }
    } //total = ["6","5","4",",","3","2","1"]
    $(id).val(total.reverse().join("") + "." + right);
}


/**
 * js禁止Backspace键使浏览器后退
 * @param e
 * @returns {boolean}
 */
function banBackSpace(e){
    var ev = e || window.event;
    //各种浏览器下获取事件对象
    var obj = ev.relatedTarget || ev.srcElement || ev.target ||ev.currentTarget;
    //按下Backspace键
    if(ev.keyCode == 8){
        var tagName = obj.nodeName //标签名称
        //如果标签不是input或者textarea则阻止Backspace
        if(tagName!='INPUT' && tagName!='TEXTAREA'){
            return stopIt(ev);
        }
        var tagType = obj.type.toUpperCase();//标签类型
        //input标签除了下面几种类型，全部阻止Backspace
        if(tagName=='INPUT' && (tagType!='TEXT' && tagType!='TEXTAREA' && tagType!='PASSWORD')){
            return stopIt(ev);
        }
        //input或者textarea输入框如果不可编辑则阻止Backspace
        if((tagName=='INPUT' || tagName=='TEXTAREA') && (obj.readOnly==true || obj.disabled ==true)){
            return stopIt(ev);
        }
    }
}
function stopIt(ev){
    if(ev.preventDefault ){
        //preventDefault()方法阻止元素发生默认的行为
        ev.preventDefault();
    }
    if(ev.returnValue){
        //IE浏览器下用window.event.returnValue = false;实现阻止元素发生默认的行为
        ev.returnValue = false;
    }
    return false;
}

/**
 * js效验数据是否为null
 */
function isNull(obj){
    if(obj!=undefined&&obj!="undefined"&&obj!=null&&obj!="null"&&obj.trim()!=""){
        return false;
    }
    return true;
}

/**
 * 查找数组中的重复项
 * @param ary1
 * @returns {Array}
 * @constructor
 */
function GetRepeat(ary1) {
    var ary = ary1.sort();//数组排序
    var cffwxmsAry = new Array();

    //所有重复元素添加进新数组内
    for(var i=0;i<ary.length;i++) {
        if (ary[i]==ary[i+1]){
            cffwxmsAry.push(ary[i]);
        }
    }
    var result = [], isRepeated;
    //对重复元素数组进行元素去重
    for (var k = 0; k < cffwxmsAry.length; k++) {
        isRepeated = false;
        for (var j = 0;j < result.length; j++) {
            if (cffwxmsAry[k] == result[j]) {
                isRepeated = true; break;
            }
        }
        if (!isRepeated) {
            result.push(cffwxmsAry[k]);
        }
    }
    return result;
}
window.holdPositions = function(obj){
        var fid = $("#userid").val();
        var fcode = "B101T90"; // 查看持仓权限
        var par = {
          fid : fid,
          fcode : fcode
        };
        $.ajax({
            url:ctx+"/account/unit-info/checkPermissions",
            type: 'post',
            data: par,
            async:false,
            success: function(result){
                if (result.data == 0&&fid!='1'){
                    layer.msg("没有该操作权限, 请联系管理员授权", {icon: 2});
                    return false;
                }else{
                    var flag = isOnline();
                    var f = obj.innerHTML;
                    var btn = obj.id;
                    if(flag) {
                        // 查看持仓
                        window.location.href = ctx + "/account/unit-bal-security/tolist?fcode=" + f
                            + "&searchValue="+ $("#uInfoSearchVal").val()//账户管理
                            + "&ubfchannelCode=" + $("#ubspChannel").val()// 报单账户
                            + "&ubfsecurityCode="+ $("#ubspfsecurityCode").val()  // 证券代码/证券名称
                            + "&ubfcode="+ $("#ubspfcode").val()   // 用户编号/用户名称
                            + "&fvarietyRightsId="+$("#vrsFvarietyRightsId").val()
                            + "&retab="+btn
                            + "&sdfsecurityCode="+$("#fsecurityCode").val()//证券持仓对比详情需要
                            + "&sdfchannelCode="+$("#fchannelCode").val()//证券持仓对比详情需要
                            + "&fdate="+$("#fdate").val();//证券持仓对比详情需要
                    }
                }
            }
        });
}


function isExitsFunction(funcName) {
    try {
        if (typeof(eval(funcName)) == "function") {
            return true;
        }
    } catch (e) {
    }
    return false;
}
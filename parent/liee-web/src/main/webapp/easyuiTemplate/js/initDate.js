Date.prototype.format = function (regExp,i18nT) {    
    var date = this;
    var i18nT = !i18nT ? 0 : i18nT;
    var i18n = [{
    				"ddd":['日', '一', '二', '三', '四', '五', '六'],
    				"dddd":['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
    				"MMM":['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
    				"MMMM":['一月份', '二月份', '三月份', '四月份', '五月份', '六月份', '七月份', '八月份', '九月份', '十月份', '十一月份', '十二月份']
   				 },{
   					"ddd":['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'],
    				"dddd":['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
    				"MMM":['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    				"MMMM":['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
    			}];
    var zeroize = function (v, l) {    
        if (!l) l = 2;
        return ("0"+v).substring(("0"+v).length-2);    
    };    
    return regExp.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {    
        switch ($0) {    
            case 'd': return date.getDate();    
            case 'dd': return zeroize(date.getDate());    
            case 'ddd': return i18n[i18nT]["ddd"][date.getDay()];    
            case 'dddd': return i18n[i18nT]["dddd"][date.getDay()];    
            case 'M': return date.getMonth() + 1;    
            case 'MM': return zeroize(date.getMonth() + 1);    
            case 'MMM': return i18n[i18nT]["MMM"][date.getMonth()];    
            case 'MMMM': return i18n[i18nT]["MMMM"][date.getMonth()];    
            case 'yy': return new String(date.getFullYear()).substr(2);    
            case 'yyyy': return date.getFullYear();    
            case 'h': return date.getHours() % 12 || 12;    
            case 'hh': return zeroize(date.getHours() % 12 || 12);    
            case 'H': return date.getHours();    
            case 'HH': return zeroize(date.getHours());    
            case 'm': return date.getMinutes();    
            case 'mm': return zeroize(date.getMinutes());    
            case 's': return date.getSeconds();    
            case 'ss': return zeroize(date.getSeconds());    
            case 'l': return date.getMilliseconds();    
            case 'll': return zeroize(date.getMilliseconds());    
            case 'tt': return date.getHours() < 12 ? 'am' : 'pm';    
            case 'TT': return date.getHours() < 12 ? 'AM' : 'PM';    
        }    
    });    
};  

/**
 * 
 * @param o	Date类型的对象 
 * @param f 格式简码
 * f = 0 : "yyyy-MM-dd"
 * f = 1 : "yyyy年MM月dd日"
 */
function formatDate(o,f) { 
	if(typeof(o) == "undefined" || o.constructor!=Date) o = new Date();
	var fmt = ["yyyy-MM-dd","yyyy年MM月dd日"];
	f = !f || isNaN(f)? 0 : (parseInt(f)>fmt.length-1 ? fmt.length: parseInt(f));
    return o.format(fmt[f]);    
}

/**
 * 
 * @param o	Date类型的对象 
 * @param f 格式简码
 * f = 0 : "yyyy-MM-dd HH-mm-ss"
 * f = 1 : "yyyy年MM月dd日 "
 */
function formatDateTime(o,f){
	if(typeof(o) == "undefined" || o.constructor!=Date) o = new Date();
	var fmt = ["yyyy-MM-dd HH:mm:ss","yyyy年MM月dd日 HH时mm分ss秒"];
	f = !f || isNaN(f)? 0 : (parseInt(f)>fmt.length-1 ? fmt.length-1: parseInt(f));
    return o.format(fmt[f]); 
}

//格式化字符串返回日期：yyyy-MM-dd 
function stringToDate(o){
	return std(o);
}

/*
 * 字符串转化为日期
 * 格式化字符串"yyyy-MM-dd hh:mm:ss"返回日期
 */
function stringToDateTime(o){
	return std(o,1);
}
function std(o,t){
	if(typeof(o) == "undefined" || typeof(o) != 'string') o = formatDateTime(new Date());
	var s = [];
	if(o.length>0) s = o.split(" ");
	var d = new Date();
	if(s.length>=1){
		var a1 = s[0].split("-");
		if(!isNaN(a1[0])) d.setFullYear(parseInt(a1[0]));   
		var month = a1[1] -1;
		if(!isNaN(a1[1]) && !isNaN(a1[2])) d.setMonth(month, a1[2]);   
	}
	if(s.length==2 && t==1){
		var a2 = s[1].split(":");
		if(!isNaN(a2[0])) d.setHours(parseInt(a2[0]));
		if(!isNaN(a2[1])) d.setMinutes(parseInt(a2[1]));
		if(!isNaN(a2[2])) d.setSeconds(parseInt(a2[2]));
	}
	return d;
}
/**
 * 加减天数
 * @param o	Date类型的对象 或string
 * @param len 加减的天数 
 * @returns Date
 */
function plusOrMinDays(len,o){
	if(typeof(o) == "undefined" || (o.constructor!=Date && o.constructor!=String)) o = new Date();
	if(o.constructor==String)  o = std(o);
	var len = !len || isNaN(len) ? 0 : parseInt(len); 
	o.setDate(o.getDate()+len);
	return o;//返回Date类型的值
}

//获得某月的天数 12表示12月
function getMonthDays(y,m){
    var d = new Date(y, m+1, 0);    
    return  d.getDate();    
}   
  
//获得本季度的开始月份   
function getQuarterStartMonth(){  
	var m = new Date().getMonth();
    var qM = 0;   
    if(m<3) qM = 0;
    if(2<m && m<6) qM = 3;  
    if(5<m && m<9) qM = 6;    
    if(m>8) qM = 9;  
    return qM;   
}   
  
//获得本周的开始日期   
function getWeekStartDate(o) {
	if(typeof(o) == "undefined" || o.constructor!=Date) o = new Date();
	o.setDate(o.getDate()-o.getDay());
    return formatDate(o);   
}   
  
//获得本周的结束日期   
function getWeekEndDate(o) {    
	if(typeof(o) == "undefined" || o.constructor!=Date) o = new Date();
	o.setDate(o.getDate()-o.getDay()+6);
    return formatDate(o);   
}   
//获得本月的开始日期   
function getMonthStartDate(o){
	if(typeof(o) == "undefined" || o.constructor!=Date) o = new Date();
	o.setDate(1);
    return formatDate(o);   
}    
  
//获得本月的结束日期   
function getMonthEndDate(o){ 
	if(typeof(o) == "undefined" || o.constructor!=Date) o = new Date();
	var m = o.getMonth();
	o.setMonth(m+1);
    o.setDate(0);
    return formatDate(o);   
}   

//获得上月开始时间
function getLastMonthStartDate(o){
	if(typeof(o) == "undefined" || o.constructor!=Date) o = new Date();
	var m = o.getMonth();
	o.setMonth(m-1);
	o.setDate(1);
  return formatDate(o); 
}

//获得上月结束时间
function getLastMonthEndDate(o){
	if(typeof(o) == "undefined" || o.constructor!=Date) o = new Date();
	o.setDate(0);
   return formatDate(o);
}
  
//获得本季度的开始日期   
function getQuarterStartDate(){   
	var d = new Date();
	d.setMonth(getQuarterStartMonth(), 1);
    return formatDate(d);   
}   
  
//获取本季度的结束日期   
function getQuarterEndDate(){ 
	var d = new Date();
	var qM = getQuarterStartMonth() + 3; 
	d.setMonth(qM, 0);
    return formatDate(d);   
}
/**
 * 
 * @param $id 
 * @param size 年数
 * @param isSelected  是否选中
 */
function initSelectYear($id,size,isSelected){
	var d = new Date();
	var y = d.getFullYear();
	var yearLength = size > 1 ? size : 1;
	var str = "";
	for(var i = -1 ;i < size;i++){
		lastY = y+i;
		str += "<option value="+lastY+">"+lastY+"</option>";
	}
	$id.html(str);
	if(isSelected)  $id.val(y);
}

function initSelectMonth($id,isSelected){
	var monArr = ["01","02","03","04","05","06","07","08","09","10","11","12"];
	var d = new Date();
	var m = d.getMonth();
	var str = "";
	for(var i = 0;i < 12;i++){
		str += "<option value="+monArr[i]+">"+monArr[i]+"</option>";
	}
	$id.html(str);
	if(isSelected)  $id.val(monArr[m]);
}

function initDateBox(formId){
	$('.startDate',formId).val(getMonthStartDate());
	$('.endDate',formId).val(getMonthEndDate());
}
function initDatTimeBox(formId,isToday){
	 var isToday = (typeof(isToday)== "undefined") ? false : true;
	 var sd,ed;
	if(isToday){
		sd = ed = formatDate();
	}else{
		sd = getMonthStartDate();
		ed = getMonthEndDate();
	}
	$('.startDate',formId).val(sd+" 00:00:00");
	$('.endDate',formId).val(ed+" 23:59:59");
}
//是否闰年
function isLeap(y) {
    return ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) ? true : false;
}

function plusOrMinusHour(h,o){
	if(typeof(o) == "undefined" || (o.constructor!=Date && o.constructor!=String)) o = new Date();
	if(o.constructor==String)  o = std(o,1);
	h = (h==undefined || isNaN(h)) ? 0 : parseInt(h);
	o.setHours(o.getHours()+h);
	return formatDateTime(o);
}

function getDateWeek(o) {
	if(typeof(o) == "undefined" || (o.constructor!=Date && o.constructor!=String)) o = new Date();
	if(o.constructor==String)  o = std(o);
	return "周"+o.format("ddd");;  
} 
function getDateDiff(start,end){
	 var startTime = std(start).getTime();     
	 var endTime = std(end).getTime();      
	 return Math.abs((startTime - endTime))/(1000*60*60*24);     
}

function returnFloat0(value) {  //将小数点清零
    value = Math.round(parseFloat(value));
    return value;
   } 

function returnFloat1(value) { //保留一位小数点
    value = Math.round(parseFloat(value) * 10) / 10;
    if (value.toString().indexOf(".") < 0)
     value = value.toString() + ".0";
    return value;
   }

function returnFloat(value){  //保留两位小数点
    value = Math.round(parseFloat(value) * 100) / 100;
    if (value.toString().indexOf(".") < 0) {
     value = value.toString() + ".00";
    }
    return value;
   }

function returnFloat2(value) { //保留两位小数点，一位小数自动补零
    value = Math.round(parseFloat(value) * 100) / 100;
    var xsd = value.toString().split(".");
    //Ext.log(xsd.length);
    if(xsd.length==1){
     value = value.toString()+".00";
     return value;
    }
    if(xsd.length>1){
     if(xsd[1].length<2){
      value = value.toString()+"0";  
     }
     return value;
    }
   } 

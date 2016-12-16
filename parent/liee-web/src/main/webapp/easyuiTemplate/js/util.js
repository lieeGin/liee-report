var utilTools = {};
utilTools.browser=(function(u){
	var obj = {"wechat" : false, "golfplus" : false,"ios" : false,"iPhone" : false,"iPad" : false,"android" : false,"mobile" :false};
	obj.wechat  = u.match(/MicroMessenger/i) == "micromessenger";
	obj.golfplus = u.indexOf('golfplus') > -1;
	obj.ios = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	obj.iPhone = u.indexOf('iphone') > -1 || u.indexOf('mac') > -1; //是否为iPhone或者QQHD浏览器
	obj.android = u.indexOf('android') > -1 || u.indexOf('linux') > -1; //android终端或者uc浏览器
	obj.iPad = u.indexOf('ipad') > -1; //是否iPad
	obj.mobile = obj.android || obj.iPhone || obj.iPad;
	return obj;
})(navigator.userAgent.toLowerCase());


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
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

var dateUtil = {
	times : [1000,60 * 1000,60 * 60 * 1000,24 * 60 * 60 * 1000,7 * 24 * 60 * 60 * 1000],
	isLeap : function (year) {
	    return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? true : false;
	},
	dateWraper :function(o){
		if(typeof(o) == "string") {
			var str = o.replace(/-/g,"/");
			o = new Date(str);
		}
		if(typeof(o) == "undefined" || o.constructor!=Date) o = new Date();
		return o;
	},
	format : function (o,regExp) { 
		if(!regExp) return o;
		o = dateUtil.dateWraper(o);
		return o.format(regExp);    
	},
	getWeek : function(o){
		return "周"+dateUtil.format(o, "ddd");    
	},
	getFullYear : function(o){
		return dateUtil.format(o, "yyyy")+"年"; 
	},
	getMonth : function(o){
		return dateUtil.format(o, "MM")+"月"; 
	},
	getDate : function(o){
		return dateUtil.format(o, "dd")+"日"; 
	},
	getHours : function(o){
		return dateUtil.format(o, "HH")+"时"; 
	},
	getMinutes : function(o){
		return dateUtil.format(o, "mm")+"分"; 
	},
	getSeconds : function(o){
		return dateUtil.format(o, "ss")+"秒"; 
	},
	addDays : function(o,len){
		return dateUtil.addTimes(o,len,3);
	},
	addHours : function(o,len){
		return dateUtil.addTimes(o,len,2);
	},
	addMinutes : function(o,len){
		return dateUtil.addTimes(o,len,1);
	},
	addSeconds : function(o,len){
		return dateUtil.addTimes(o,len,0);
	},
	addTimes : function(o,len,units){
		len = len || 0;
		units = units || 0;
		o = dateUtil.dateWraper(o);
		var time = len * dateUtil.times[units];
		o.setTime(o.getTime()+time);
		return o;
	},
	getDays : function(o){
		o = dateUtil.dateWraper(o);
		return new Date(o.getFullYear(), o.getMonth()+1, 0).getDate();
	}
};

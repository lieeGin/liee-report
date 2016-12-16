/**
 * js扩展工具类
 * 
 * wanglei
 */
var sysUtil = $.extend({}, sysUtil);/* 全局对象 */

$.parser.onComplete = function() {/* 页面所有easyui组件渲染成功后，隐藏等待信息 */
	if ($.browser.msie && $.browser.version < 7) {/* 解决IE6的PNG背景不透明BUG */
		sy.pngFun();
		sy.bgPngFun($('span'));
	}
	window.setTimeout(function() {
		$.messager.progress('close');
	}, 1000);
};

$.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 */
	var frame = $('iframe', this);
	if (frame.length > 0) {
		frame[0].contentWindow.document.write('');
		frame[0].contentWindow.close();
		frame.remove();
		if ($.browser.msie) {
			CollectGarbage();
		}
	}
};

$.fn.panel.defaults.loadingMessage = '数据加载中，请稍候。。。。';

$.fn.datagrid.defaults.onLoadError = function(XMLHttpRequest) {
	$.messager.progress('close');
	alert(XMLHttpRequest.responseText);
};

$.fn.combogrid.defaults.onLoadError = function(XMLHttpRequest) {
	$.messager.progress('close');
	alert(XMLHttpRequest.responseText);
};

$.fn.combobox.defaults.onLoadError = function(XMLHttpRequest) {
	$.messager.progress('close');
	alert(XMLHttpRequest.responseText);
};

//$.fn.form.defaults = {
//	onSubmit : function() { //由于本项目没有使用easyui的form提交，只是使用easyui的表单验证，所以不需要onSubmit方法，屏蔽此方法可以解决当表单里只有一个input和一个textarea，并且input是validate的，点击回车按钮会发生空提交事件的bug 
//		return false;
//	}
//};

$.extend($.fn.validatebox.defaults.rules, {
	eqPassword : {/* 扩展验证两次密码 */
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});
$.extend($.fn.validatebox.defaults.rules, {
	minLength : { // 判断最小长度
		validator : function(value, param) {
			return value.length >= param[0];
		},
		message : '最少输入 {0} 个字符。'
	},
	length:{validator:function(value,param){
		var len=$.trim(value).length;
			return len>=param[0]&&len<=param[1];
		},
			message:"内容长度介于{0}和{1}之间."
		},
	phone : {// 验证电话号码
		validator : function(value) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '格式不正确,请使用下面格式:020-88888888'
	},
	mobile : {// 验证手机号码
		validator : function(value) {
			return /^(13|15|18)\d{9}$/i.test(value);
		},
		message : '手机号码格式不正确(正确格式如：13450774432)'
	},
	phoneOrMobile:{//验证手机或电话
		validator : function(value) {
			return /^(13|15|18)\d{9}$/i.test(value) || /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message:'请填入手机或电话号码,如13688888888或020-8888888'
	},
	idcard : {// 验证身份证
		validator : function(value) {
			return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
		},
		message : '身份证号码格式不正确'
	},
	floatOrInt : {// 验证是否为小数或整数
		validator : function(value) {
			return /^(\d{1,3}(,\d\d\d)*(\.\d{1,3}(,\d\d\d)*)?|\d+(\.\d+))?$/i.test(value);
		},
		message : '请输入数字，并保证格式正确'
	},
	currency : {// 验证货币
		validator : function(value) {
			return /^d{0,}(\.\d+)?$/i.test(value);
		},
		message : '货币格式不正确'
	},
	qq : {// 验证QQ,从10000开始
		validator : function(value) {
			return /^[1-9]\d{4,9}$/i.test(value);
		},
		message : 'QQ号码格式不正确(正确如：453384319)'
	},
	integer : {// 验证整数
		validator : function(value) {
			return /^\d+$/i.test(value);
		},
		message : '请输入整数'
	},
	chinese : {// 验证中文
		validator : function(value) {
			return /^[\u0391-\uFFE5]+$/i.test(value);
		},
		message : '请输入中文'
	},
	english : {// 验证英语
		validator : function(value) {
			return /^[A-Za-z]+$/i.test(value);
		},
		message : '请输入英文'
	},
	unnormal : {// 验证是否包含空格和非法字符
		validator : function(value) {
			return /.+/i.test(value);
		},
		message : '输入值不能为空和包含其他非法字符'
	},
	username : {// 验证用户名
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
		},
		message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
	},
	password : {// 验证密码
		validator : function(value) {
			return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
		},
		message : '密码不合法（字母开头，允许6-16字节，允许字母数字下划线）'
	},
	faxno : {// 验证传真
		validator : function(value) {
//			return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
			return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
		},
		message : '传真号码不正确'
	},
	zip : {// 验证邮政编码
		validator : function(value) {
			return /^[1-9]\d{5}$/i.test(value);
		},
		message : '邮政编码格式不正确'
	},
	ip : {// 验证IP地址
		validator : function(value) {
			return /d+.d+.d+.d+/i.test(value);
		},
		message : 'IP地址格式不正确'
	},
	name : {// 验证姓名，可以是中文或英文
			validator : function(value) {
				return /^[\u0391-\uFFE5]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value);
			},
			message : '请输入姓名'
	},
	carNo:{
		validator : function(value){
			return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value); 
		},
		message : '车牌号码无效（例：粤J12350）'
	},
	carenergin:{
		validator : function(value){
			return /^[a-zA-Z0-9]{16}$/.test(value); 
		},
		message : '发动机型号无效(例：FG6H012345654584)'
	},
	email:{
		validator : function(value){
		return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
	},
	message : '请输入有效的电子邮件账号(例：abc@126.com)'	
	},
	msn:{
		validator : function(value){
		return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
	},
	message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
	},department:{
		validator : function(value){
			return /^[0-9]*$/.test(value); 
		},
		message : '请输入部门排序号(例：1)'	
	},same:{
		validator : function(value, param){
			if($("#"+param[0]).val() != "" && value != ""){
				return $("#"+param[0]).val() == value; 
			}else{
				return true;
			}
		},
		message : '两次输入的密码不一致！'	
	}
});
$.extend($.fn.form.methods, {  
    serialize: function(jq){  
        var arrayValue = $(jq[0]).serializeArray();
		var json = {};
		$.each(arrayValue, function() {
			var item = this;
			if (json[item["name"]]) {
				json[item["name"]] = json[item["name"]] + "," + item["value"];
			} else {
				json[item["name"]] = item["value"];
			}
		});
		return json; 
    },
    getValue:function(jq,name){  
        var jsonValue = $(jq[0]).form("serialize");
		return jsonValue[name]; 
    },
    setValue:function(jq,name,value){
		return jq.each(function () {
				_b(this, _29);
				var data = {};
				data[name] = value;
				$(this).form("load",data);
		});
	}
}); 
$.extend($.fn.datagrid.methods, {  
    addToolbarItem: function(jq, items){  
        return jq.each(function(){  
            var toolbar = $(this).parent().prev("div.datagrid-toolbar");
            for(var i = 0;i<items.length;i++){
                var item = items[i];
                if(item === "-"){
                    toolbar.append('<div class="datagrid-btn-separator"></div>');
                }else{
                    var btn=$("<a href=\"javascript:void(0)\"></a>");
                    btn[0].onclick=eval(item.handler||function(){});
                    btn.css("float","left").appendTo(toolbar).linkbutton($.extend({},item,{plain:true}));
                }
            }
            toolbar = null;
        });  
    },
    removeToolbarItem: function(jq, param){  
        return jq.each(function(){  
            var btns = $(this).parent().prev("div.datagrid-toolbar").find("a");
            var cbtn = null;
            if(typeof param == "number"){
                cbtn = btns.eq(param);
            }else if(typeof param == "string"){
                var text = null;
                btns.each(function(){
                    text = $(this).data().linkbutton.options.text;
                    if(text == param){
                        cbtn = $(this);
                        text = null;
                        return;
                    }
                });
            }
            if(cbtn){
                var prev = cbtn.prev()[0];
                var next = cbtn.next()[0];
                if(prev && next && prev.nodeName == "DIV" && prev.nodeName == next.nodeName){
                    $(prev).remove();
                }else if(next && next.nodeName == "DIV"){
                    $(next).remove();
                }else if(prev && prev.nodeName == "DIV"){
                    $(prev).remove();
                }
                cbtn.remove();    
                cbtn= null;
            }                        
        });  
    }                
});
/**
 * 增加命名空间功能
 * 
 * 使用方法：sysUtil.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
sysUtil.ns = function() {
	var o = {}, d;
	for ( var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for ( var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};

/**
 * 增加formatString功能
 * 
 * 使用方法：sy.fs('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 */
sysUtil.fs = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};
	

/**
 * 获得项目根路径
 * 
 * 使用方法：sy.bp();
 */
sysUtil.bp = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};

/**
 * 生成UUID
 */
sysUtil.UUID = function() {
	var s = [], itoh = '0123456789ABCDEF';
	for ( var i = 0; i < 36; i++)
		s[i] = Math.floor(Math.random() * 0x10);
	s[14] = 4;
	s[19] = (s[19] & 0x3) | 0x8;
	for ( var i = 0; i < 36; i++)
		s[i] = itoh[s[i]];
	s[8] = s[13] = s[18] = s[23] = '-';
	return s.join('');
};

/**
 * 获得URL参数
 */
sysUtil.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

/**
 * 用于火狐浏览器调试
 */
sysUtil.debug = function(o) {
	if (navigator.userAgent.indexOf('Firefox') > -1) {
		//console.info(o);/* 正式项目将此行注释即可 */
	}
};

/**
 * 调试DOM文档是否有重复ID
 */
sysUtil.debugIdLength = function() {
	var t = '';
	$.each($('[id]'), function(index, item) {
		if (item.id && item.id != 'undefined') {
			var i = $('[id="' + item.id + '"]').length;
			if (i > 1 && item.id != t) {
				sy.debug('查到重复ID，ID名称【' + item.id + '】，重复【' + i + '】次！');
				t = item.id;
			}
		}
	});
	if (t == '') {
		sysUtil.debug('没有重复ID！');
	}
};

/**
 * 查看DOM节点数目
 */
sysUtil.debugDomLength = function() {
	sy.debug('有【' + $('div').length + '】个DIV');
};

$.ajaxSetup({
	type : "POST",
	complete:function(XMLHttpRequest,textStatus){
        var sessionStatus=XMLHttpRequest.getResponseHeader("sessionstatus"); 
        if(sessionStatus=="timeOut"){
             //这里怎么处理在你，这里跳转的登录页面
             window.location.reload(true);
        }
     },
	error : function(XMLHttpRequest, textStatus, errorThrown) {/* 扩展AJAX出现错误的提示 */
		 var sessionStatus=XMLHttpRequest.getResponseHeader("sessionstatus");
		$.messager.progress('close');
		 if(sessionStatus!="timeOut") alert(XMLHttpRequest.responseText);
	}
});
var temp = false;
var easyuiPanelOnMove = function(left, top) {/* 防止超出浏览器边界 */
		if(temp == true){
			temp = false;
			return false;
		}
		if (left < 0) {
			temp = true;
			$(this).window('move', {
				left : 1
			});
		}
		if (top < 0) {
			temp = true;
			$(this).window('move', {
				top : 1
			});
		}
		var width = $(this).panel('options').width;
		var height = $(this).panel('options').height;
		var right = left + width;
		var buttom = top + height;
		var browserWidth = $(document).width();
		var browserHeight = $(document).height();
		if (right > browserWidth) {
			temp = true;
			$(this).window('move', {
				left : browserWidth - width
			});
		}
		if (buttom > browserHeight) {
			temp = true;
			$(this).window('move', {
				top : browserHeight - height
			});
		}
	};
	$.fn.panel.defaults.onMove = easyuiPanelOnMove;
	$.fn.window.defaults.onMove = easyuiPanelOnMove;
	$.fn.dialog.defaults.onMove = easyuiPanelOnMove;

function pageLoader(param,success,error){
	var that = $(this);
	var opts = that.datagrid("options");
	if (!opts.url) {
		return false;
		}
	var cache = that.data().datagrid.cache;
		if (!cache) {
			$.ajax({
				type : opts.method,
				url : opts.url,
				data : param,
				dataType : "json",
				success : function (data) {
					data = opts.loadFilter(data);
					that.data().datagrid['cache'] = data;
					success(bulidData(data));
			},
			error : function () {
				error.apply(this, arguments);
			}
			});
		} else {
			success(bulidData(cache));
		}
		
		function bulidData(data) {
			var temp = $.extend({},data);
			var tempRows = [];
			var start = (param.page - 1) * parseInt(param.rows);
			var end = start + parseInt(param.rows);
			var rows = data.rows;
			for (var i = start; i < end; i++) {
				if(rows[i]){
					tempRows.push(rows[i]);
				}else{
					break;
				}
			}
			temp.rows = tempRows;
			return temp;
		}
	};
function initDateCombobox(){
	var date = new Date();
 	var fullYear = date.getFullYear();
 	var mouth = date.getMonth()+1;
 	var day = date.getDate();
 	var newDate = new Date(fullYear,mouth,1);
 	var day2 = (new Date(newDate.getTime()-1000*60*60*24)).getDate();
 	if(day<10){
 		day="0"+day;
 	}
 	if(day2<10){
 		day2="0"+day2;
 	}
 	if(mouth<10){
 		mouth="0"+mouth;
 	}
	var start = $('#startDate').val();
	var end = $('#endDate').val();
	if(""==start){
		 start = fullYear+"-"+mouth+"-"+"01";
		 if($('#startDate').attr("class").indexOf("time")>-1){
			 $('#startDate').val(start+" 00:00:00"); 
		 }else{
			 $('#startDate').val(start);
		 }
	}
	if(""==end){
		end = fullYear+"-"+mouth+"-"+day2;
		 if($('#endDate').attr("class").indexOf("time")>-1){
			 $('#endDate').val(end+" 23:59:59"); 
		 }else{
			 $('#endDate').val(end); 
		 }
	}
}
function selectInit(list,$id,initOption,idField,valueField){
	if(!idField){
		idField = "id";
	}
	if(!valueField){
		valueField = "value";
	}
	if(list&&list.length>=0){
		var str="";
		var head = "<option value=''></option>";
		for(var i=0;i<list.length;i++){
			if(initOption==list[i][idField]){
				head = "<option value="+list[i][idField]+">"+(list[i][valueField]?list[i][valueField]:'-')+"</option>";
			}else{
				str+="<option value="+list[i][idField]+">"+(list[i][valueField]?list[i][valueField]:'-')+"</option>";
			}
		}
		$id.html(head+str);
		$id.trigger("liszt:updated");
	}
}

function changeProvinceId(provinceId,$id,cityId){
	$.ajax({  
		type: "post",  
		url: "city!needCityIdByProvinceId.action",
		async:false,
		data:{"provinceId":provinceId},
		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		dataType:"json",                          
		success: function(data){
			var list = data["cityIdList"];
			selectInit(list,$id,cityId);
		}
	});
}
function changeAgentIdgetCourse(agentId,$id){
	$.ajax({  
		type: "post",  
		url: "tools!getAgentCourse.action",
		async:false,
		data:{"agentId":agentId},
		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		dataType:"json",                          
		success: function(data){
			var list = data["courseNameList"];
			selectInit(list,$id,"");
		}
	});
}
function initOption(list){
	var str = "";
	for(var i=0;i<list.length;i++){
		str+="<option value="+list[i]['id']+">"+list[i]['value']+"</option>";
		}
	return str;
}
function initOption2(list,id,value){
	var str = "";
	for(var i=0;i<list.length;i++){
		str+="<option value="+list[i][id]+">"+list[i][value]+"</option>";
		}
	return str;
}
function getIds(objs,field){
	var ids ="";
	for(var i=0;i<objs.length;i++){
		ids+=","+objs[i][field];
	}
	if(ids.length>0) ids = ids.substring(1);
	return ids;
}
function sumDataByProperties(inObjs,inFields,outObj){
	if(inObjs==null || inObjs.length==0 || inFields==null || inFields.length==0 || outObj==null) return;
	for(var i=0;i<inFields.length;i++) {
		var num=0;
		for(var j=0;j<inObjs.length;j++) {
			var tmp=Number(inObjs[j][inFields[i]]);
			num += tmp;
		}
		outObj[inFields[i]] = num;
	}
}
//设置高度为返回值的高度
function getDocumentHeight(rate,height){//rate：传入百分比 //height 传入高度
	var documentHeight = $(window).height();
	return documentHeight*rate>height?height:documentHeight*rate;
}
//设置宽度为返回值的宽度
function getDocumentWidth(rate,width){//rate：传入百分比 (默认传入1)//width 传入宽度
	var documentWidth = $(window).width();
	return documentWidth*rate>width?width:documentWidth*rate;
}
function list2map(list) {
	var map={};
	if(!list || !list.length) return map;
	for(var i=0;i<list.length;i++){
		map[list[i]['id']]=list[i]['value'];
	}
	return map;
}

function list2map(list,idField,valueField) {
	
	if(!idField){
		idField = "id";
	}
	if(!valueField){
		valueField = "value";
	}
	var map={};
	if(!list || !list.length) return map;
	for(var i=0;i<list.length;i++){
		map[list[i][idField]]=list[i][valueField];
	}
	return map;
}

function initAutoComplete($selector,url,callBack){
	$selector.autocomplete(url).result(function(event, row, formatted) {
		   $(this).siblings("input[type=hidden]").first().val(row && row[1] ? row[1] : "");
		    if(callBack) callBack();
	   }).bind("blur",function() {
		   if(!$(this).val()) {
			   $(this).siblings("input[type=hidden]").first().val('');
			   if(callBack) callBack();
		   }
	   });
}

/**
 * 
 * @param file 
 * @param preImgContainer 预览容器
 * @param preImgId 预览图片Id
 * @param width
 * @param height
 */
function previewImage(file, preImgContainer, preImgId, width, height) {
	var MAXWIDTH = width;
	var MAXHEIGHT = height;
	var div = document.getElementById(preImgContainer);
	if (file.files && file.files[0]) {
		div.innerHTML = '<img id='+preImgId+'>';
		var img = document.getElementById(preImgId);
		img.onload = function() {
			var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
					img.offsetWidth, img.offsetHeight);
			img.width = rect.width;
			img.height = rect.height;
			img.style.marginLeft = rect.left + 'px';
			img.style.marginTop = rect.top + 'px';
		};
		var reader = new FileReader();
		reader.onload = function(evt) {
			img.src = evt.target.result;
		};
		reader.readAsDataURL(file.files[0]);
	} else {
		var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
		file.select();
		var src = document.selection.createRange().text;
		div.innerHTML = '<img id='+preImgId+'>';
		var img = document.getElementById(preImgId);
		img.filters
				.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
		var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT,
				img.offsetWidth, img.offsetHeight);
		status = ('rect:' + rect.top + ',' + rect.left + ','
				+ rect.width + ',' + rect.height);
		div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;margin-left:"+rect.left+"px;"+sFilter+src+"\"'></div>";
	}
}
function clacImgZoomParam(maxWidth, maxHeight, width, height) {
	var param = {
		top : 0,
		left : 0,
		width : width,
		height : height
	};
	if (width > maxWidth || height > maxHeight) {
		rateWidth = width / maxWidth;
		rateHeight = height / maxHeight;

		if (rateWidth > rateHeight) {
			param.width = maxWidth;
			param.height = Math.round(height / rateWidth);
		} else {
			param.width = Math.round(width / rateHeight);
			param.height = maxHeight;
		}
	}

	param.left = Math.round((maxWidth - param.width) / 2);
	param.top = Math.round((maxHeight - param.height) / 2);
	return param;
}


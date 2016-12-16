var base = $.extend({}, base);/* 全局对象 */
var trueOrFalseMap={"0":"否","1":"是"};
/**
 *   ajax 加载 select
 */
base.select = function(el,url,valueField,textField,defaultValue) {
	var $el = $("#"+el);
	
	$.ajax({
		type : "post",
		async : true,
		url : sysUtil.bp()+ url,
		contentType : "application/x-www-form-urlencoded;charset=UTF-8",
		dataType : "json",
		success : function(data) {
			if(data){
				for(var i = 0;i <data.length ; i++){
					var o = data[i];
					
					var isSelect ='';
					if(defaultValue==o[valueField]){
						isSelect='select=true';
					}
					var optionStr = '<option value="'+o[valueField]+'" '+isSelect+'>'+o[textField]+'</option>';
					$el.append(optionStr);
				}
			}
		}
	});
};

/**
 *   自动带上menuId的ajax请求( 用于需要读写权限控制的请求 )
 *   调用该方法的js文件或者jsp文件中，一定要有一个名为menuId的变量
 */
base.authAjax = function(config){
	var data = config.data;
	if(!data.menuId && menuId){
		data.menuId=menuId;
	}
	$.ajax(config);
}


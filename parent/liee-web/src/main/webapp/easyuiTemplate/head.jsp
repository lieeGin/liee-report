<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	request.getSession().setAttribute("path", path);
	request.getSession().setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<link rel="stylesheet" href="${path}/easyuiTemplate/css/default.css" type="text/css">
	<link rel="stylesheet" href="${path}/easyuiTemplate/css/main.css" type="text/css">
    <link rel="stylesheet" href="${path}/easyuiTemplate/css/chosen.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="${path}/easyuiTemplate/jquery-easyui/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css" href="${path}/easyuiTemplate/jquery-easyui/themes/icon.css">
    <script type="text/javascript" src="${path}/easyuiTemplate/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${path}/easyuiTemplate/jquery-easyui/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="${path}/easyuiTemplate/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
  	<script type="text/javascript" src="${path}/easyuiTemplate/js/sysUtils.js"></script>
  	<script type="text/javascript" src="${path}/easyuiTemplate/js/util.js"></script>
  	<script type="text/javascript" src="${path}/easyuiTemplate/js/chosen.jquery.js"></script>
  	<script type="text/javascript" src="${path}/easyuiTemplate/js/jquery.form.js"></script>
 	<script type="text/javascript" src="${path}/easyuiTemplate/js/initDate.js"></script>
   	<script type="text/javascript" src="${path}/easyuiTemplate/js/base.js"></script>
  	

  <script>
	$(document).ajaxComplete(function(event, xhr, settings) {
	    if(xhr.getResponseHeader("sessionstatus")=="timeOut"){  // 登录超时
	        if(xhr.getResponseHeader("loginPath")){
	          //  alert("会话过期，请重新登陆!");
	            window.parent.location.href=xhr.getResponseHeader("loginPath");
	        }else{  
	        	  parent.BootstrapDialog.alert('请求超时请重新登陆 !');
	          //  alert("");  
	        }  
	    }else if(xhr.getResponseHeader("sessionstatus")=="hasNoAccess"){  // 没有操作权限
	    	 //  没有操作权限时，提交的按钮需要重新放开  如果有多种按钮，需要分别处理
	    	if($("#saveBtn") && $("#saveBtn").hasClass("easyui-linkbutton")){ 
	    		 $("#saveBtn").linkbutton("enable");
	    	}
	    	if($("#submit") && $("#submit").hasClass("easyui-linkbutton")){
	    		 $("#submit").linkbutton("enable");
	    	}
	    	if($("#update") && $("#submit").hasClass("easyui-linkbutton")){
	    		 $("#update").linkbutton("enable");
	    	}
	    	
	    	/* $.messager.show({
				title : '提示',
				msg : '对不起，你没有该操作的权限',
				timeout : 3000
			}); */
	    }
	});  
  </script>
</head>
<body>
</body>
</html>

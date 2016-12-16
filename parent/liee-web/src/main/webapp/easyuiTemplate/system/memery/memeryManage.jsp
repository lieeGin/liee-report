<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/easyuiTemplate/head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>系统缓存管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<script>
	   // 权限控制使用到以下2个参数
  		var authLevel="${param.authLevel}";
  		var menuId="${param.menuId}";
  	</script>
  	<script type="text/javascript" src="${path}/easyuiTemplate/system/memery/memeryManage.js"></script>
  
  </head>
  <body>
 <body class="easyui-layout" style="overflow:hidden" loadingMessage="请等待数据加载....." fit="true">
	  	<div region="center" border="true">
	  		<div style="width:98%;height:90%;margin:10 10 10 10">
		  		<table id="userMenuAuthGrid" class="esayui"></table>
	  		</div>
		</div>
  </body>
</html>
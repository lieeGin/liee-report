<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/easyuiTemplate/head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>系统参数管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<script>
	   // 权限控制使用到以下2个参数
  		var authLevel="${param.authLevel}";
  		var menuId="${param.menuId}";
  	</script>
  	<script type="text/javascript" src="${path}/easyuiTemplate/system/systemconfig/systemconfig.js"></script>
  </head>
  <body>
 <body class="easyui-layout" style="overflow:hidden" loadingMessage="请等待数据加载....." fit="true">
	  	<div region="center" border="true">
	  		 <form id="selectForm">
	  		 		<table style="font-size:12px;margin-left:10px;">
	  		 		<tr>
	  		 			<td>参数编号：</td>
	  					<td><input name="keyword" type="text"/></td>
	  					<td>参数描述：</td>
	  					<td><input name="remark" type="text"/></td>
			  	 	 	<td><a href="javascript:void(0)" plain="true" id="select" class="easyui-linkbutton" iconCls="icon-search">查询</a> </td>
			  	 	 </tr>
	  		 	</table>
			  </form>
	  		<div style="width:98%;height:90%;margin:10 10 10 10">
		  		<table id="dataGrid" class="esayui"></table>
	  		</div>
		</div>
		<div id="editDialog" class="easyui-dialog" style="width: 400px;height: 300px;" title="详细信息" style="dispaly:none">
	  			<form id="editForm" >
	  					<table class="tableStyle">
	  						<tr>
	  							<td align="right" class="tdStyle">参数编号:</td>
	  							<td align="left" class="tdStyle">
	  								<input type="hidden" name="id">
	  								<input type="text" name="keyword" class="easyui-validatebox" required="true" style="font-size:12px;width:300px;"/>
	  							</td>
	  						</tr>
	  						<tr>
	  							<td align="right" class="tdStyle">参数描述:</td>
	  							<td align="left" class="tdStyle">
	  								<input type="text" name="remark" class="easyui-validatebox"  style="font-size:12px;width:300px;"/>
	  							</td>
	  						</tr>
	  						<tr>
	  							<td align="right" class="tdStyle">参数值:</td>
	  							<td align="left" class="tdStyle">
	  								<input type="text" name="valuestr" class="easyui-validatebox"  style="font-size:12px;width:300px;"/>
	  							</td>
	  						</tr>
	  						 <tr>
  							<td align="left" class="tdStyle" colspan="4">
  								<span id="errorMessage" style="font-size:16px;color:red;padding-left:10px;"></span>
  							</td>
  						</tr>
	  					</table>
	  			</form>
	  		</div>
  </body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/easyuiTemplate/head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>菜单管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<script>
	   // 权限控制使用到以下2个参数
  		var authLevel="${param.authLevel}";
  		var menuId="${param.menuId}";
  	</script>
  	<script type="text/javascript" src="${path}/easyuiTemplate/system/menu/sysMenu.js"></script>
  </head>
  <body>
 <body class="easyui-layout" style="overflow:hidden" loadingMessage="请等待数据加载....." fit="true">
 		
	  	<div region="center" border="true">
	  		<div style="float:left;width:30%;height:90%;margin-top:5px;margin-left:5px">
	  			<div style="padding:5px;width:500px;">
					<a href="#" id="addSub" class="easyui-linkbutton" iconCls="icon-add">新增下级</a>
					<a href="#" id="reload" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
				</div>
	 			<ul id="menuTree" class="easyui-tree"></ul>
	 		</div>
	  		<div style="float:left;width:60%;height:90%;margin-top:5px;margin-left:5px">
		  		 <form id="editForm" >
	  					<table class="tableStyle">
	  						<tr>
	  							<td align="right" class="tdStyle">菜单名称:
	  								<input type="hidden" name="id" id="id">
	  								<input type="hidden" name="level" id="level">
	  								<input type="hidden" name="parentId" id="parentId">
	  							</td>
	  							<td align="left" class="tdStyle">
	  								<input type="text" name="menuName" id="menuName" class="easyui-validatebox"  style="font-size:12px;width:300px;" required="true"/>
	  							</td>
	  						</tr>
	  						<tr>
	  							<td align="right" class="tdStyle">菜单路径:</td>
	  							<td align="left" class="tdStyle">
	  								<input type="text" name="url" id="url" class="easyui-validatebox"  style="font-size:12px;width:300px;" />
	  							</td>
	  						</tr>
	  						<tr>
	  							<td align="right" class="tdStyle">显示顺序:</td>
	  							<td align="left" class="tdStyle">
	  								<input type="text" name="displayOrder" id="displayOrder" class="numberbox"  style="font-size:12px;width:300px;" precision="0"  />
	  							</td>
	  						</tr>
	  						 <tr>
	  							<td align="left" class="tdStyle" colspan="4">
	  								<span id="errorMessage" style="font-size:16px;color:red;padding-left:10px;"></span>
	  							</td>
  							</tr>
  							<tr>
	  							<td align="right" class="tdStyle"></td>
	  							<td align="left" class="tdStyle">
	  								<a href="#" id="submit" class="easyui-linkbutton" iconCls="icon-save">保存</a>
	  								<a href="#" id="delete" class="easyui-linkbutton" iconCls="icon-cancel">删除</a>
	  							</td>
	  						</tr>
	  					</table>
	  			</form>
			  </div>
		</div>
  </body>
</html>
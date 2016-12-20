<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/easyuiTemplate/head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>自定义报表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<script>
	   // 权限控制使用到以下2个参数
  		var authLevel="${param.authLevel}";
  		var menuId="${param.menuId}";
  	</script>
  	<script type="text/javascript" src="${path}/easyuiTemplate/report/config/reportconfig.js"></script>
  	<!-- 代码高亮 -->
  	<link rel="stylesheet" href="//cdn.jsdelivr.net/highlight.js/9.8.0/styles/default.min.css">
	
  </head>
  <body>
 <body class="easyui-layout" style="overflow:hidden" loadingMessage="请等待数据加载....." fit="true">
	  	<div region="center" border="true">
	  		 <form id="selectForm">
	  		 		<table style="font-size:12px;margin-left:10px;">
	  		 		<tr>
	  		 			<td>报表名称：</td>
	  					<td><input name="name" type="text" class="easyui-validatebox" /></td>
	  					<td>报表编号：</td>
	  					<td><input name="code" type="text"class="easyui-validatebox" /></td>
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
	  				<table style="width:100%">
	  					<tr>
	  						<td style="width:50%;top:0px;" rowspan="2" >
			  					<table class="tableStyle" style="width:100%">
			  						<tr>
			  							<td align="right" class="tdStyle">名称:</td>
			  							<td align="left" class="tdStyle">
			  								<input type="hidden" name="id">
			  								<input type="hidden" name="groovyFile">
			  								<input type="text" name="name" class="easyui-validatebox" required="true" style="font-size:12px;width:150px;"/>
			  							</td>
			  							<td align="right" class="tdStyle">编号:</td>
			  							<td align="left" class="tdStyle">
			  								<input type="text" name="code" class="easyui-validatebox" required="true" style="font-size:12px;width:150px;"/>
			  							</td>
			  						</tr>
			  						<tr>
			  							<td align="right" class="tdStyle">数据来源:</td>
			  							<td align="left" class="tdStyle">
			  								<select name ="dataSource" class="easyui-combobox" required="true" style="font-size:12px;width:150px; " panelHeight="100">
			  									<option value="0">groovy脚本查询</option>
			  									<option value="1">程序接口</option>
			  								</select>
			  							</td>
			  						</tr>
			  						<tr>
			  							<td align="right" class="tdStyle">接口地址:</td>
			  							<td align="left" class="tdStyle" colspan="3">
			  								<input type="text" name="apiAddress" class="easyui-validatebox"  style="font-size:12px;width:90%;"/>
			  							</td>
			  						</tr>
			  						<tr>
			  							<td align="right" class="tdStyle">展示模式:</td>
			  							<td align="left" class="tdStyle" colspan="3">
			  								    <input type="checkbox" name="reportShowWay" value="0"   />表格
								                <input type="checkbox" name="reportShowWay" value="1"  />柱状图<br />
								                <input type="checkbox" name="reportShowWay" value="2" />曲线图
								                <input type="checkbox" name="reportShowWay" value="3"  />雷达图<br />
								                <input type="checkbox" name="reportShowWay" value="4"  />饼图
			  							</td>
			  						</tr>
			  						<tr>
			  							<td align="right" class="tdStyle">groovy脚本:</td>
			  							<td align="left" class="tdStyle"  colspan="3">
			  								<textarea name="groovyStr" id="groovyStr" cols="80" rows="30" required="true" onblur="changeShow(1)"  style="width:90%"></textarea>
			  								
			  								<pre id="codeArea" style="height:420px;width:90%;display:none" >
				  								<code class="groovy" style="height:420px;">
				  								</code>
			  								</pre >
			  							</td>
			  						</tr>
			  						 <tr>
		  							<td align="left" class="tdStyle" colspan="4">
		  								<span id="errorMessage" style="font-size:16px;color:red;padding-left:10px;"></span>
		  							</td>
		  						</tr>
		  					</table>
	  					</td>
	  					
	  					<td style="width:50%;height:50%">
	  						查询参数配置：
		  					<table id="paramGrid" class="esayui" ></table>
	  					</td>
  					</tr>
  					<tr>
  						<td>
  							查询结果配置：
  							<table id="columnGrid" class="esayui"></table>
  						</td>
  					</tr>
				</table>
  			</form>
  		</div>
  		<script src="//cdn.jsdelivr.net/highlight.js/9.8.0/highlight.min.js"></script>
  		<script >hljs.initHighlightingOnLoad();</script>  
  </body>
</html>
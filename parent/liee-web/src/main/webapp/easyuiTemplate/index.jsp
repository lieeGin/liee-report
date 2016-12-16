<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="./head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>lieeGin后台管理系统</title>
		<script type="text/javascript" src="${path}/easyuiTemplate/js/index.js"></script>
		<style type="text/css">
.l-topmenu {
	margin: 0;
	padding: 0;
	height: 31px;
	line-height: 31px;
	background: #0068b7;
	position: relative;
	border-top: 1px solid #1D438B;
}

.l-link2 {
	text-decoration: underline;
	color: white;
	margin-left: 2px;
	margin-right: 2px;
}

.l-topmenu-logo {
	color: #E7E7E7;
	padding-left: 35px;
	line-height: 26px;
	background: url('${path}/easyuiTemplate/images/topicon.png') no-repeat 10px 5px;
}

.l-topmenu-welcome {
	position: absolute;
	height: 24px;
	line-height: 24px;
	right: 30px;
	top: 2px;
	color: #070A0C;
}

.l-topmenu-welcome a {
	color: #E7E7E7;
	text-decoration: underline
}
</style>

	</head>
	<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
		<noscript>
			<div
				style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
				<img src="${path}/easyuiTemplate/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
			</div>
		</noscript>
		<div region="north" border="false" class="l-topmenu">
			<div class="l-topmenu-logo">
				桌球联盟信息管理系统
			</div>
			<div class="l-topmenu-welcome">
				<span class="space"></span>
				<a href="javascript:void(0)" class="l-link2"
					id="loginOut">安全退出</a>
				<span class="space">|</span>
				<a href="#" class="l-link2" target="_blank">桌球联盟官网</a>
				<span class="space">|</span>
				<a href="javascript:void(0)" class="l-link2"
					id="updatePwd">修改密码</a>
			</div>
		</div>
		<div region="south" split="false"
			style="height: 25px; background:  #efefef;">
			<div class="footer" style="text-align: center">
				Copyright © 2016 桌球联盟
			</div>
		</div>
		<div region="west" hide="true" split="true" title="导航菜单"
			style="width: 170px;" id="west">
			<div id="nav" class="easyui-accordion" border="false" style="overflow:visible;">

			</div>
		</div>
		<div id="mainPanle" region="center"
			style="background: #eee;">
			<div id="tabs" class="easyui-tabs" fit="true" border="false">
			</div>
		</div>
		<div id="pwdDialog" title="修改密码" style="width:450px;height:200px;dispaly:none">
	  			<form id="userUpdatePwdForm">
		  			<table style="margin-left:100px;align:center">
		  				<tr>
		  					<td align="right"><label for="userIdEdit">用户账号：</label></td>
		  					<td><input type="text" id="userIdEdit" name="userName" value="<%=session.getAttribute("userName")%>" readOnly="true"/></td>
		  					<td></td>
		  				</tr>
		  				<tr>
		  					<td align="right"><label for="userNameEdit">用户名称：</label></td>
		  					<td><input type="text"  id="userNameEdit" name="displayName" value="<%=session.getAttribute("displayName")%>" readOnly="true"/></td>
		  					<td></td>
		  				</tr>
		  				<tr>
		  					<td><label for="userPwdOldEdit">用户旧密码：</label></td>
		  					<td><input class="easyui-validatebox" type="password" required="true" id="userPwdOldEdit" name="userOldPwd"/></td>
		  					<td></td>
		  				</tr>
		  				<tr>
		  					<td><label for="userPwdEdit">用户新密码：</label></td>
		  					<td><input class="easyui-validatebox" type="password" required="true" id="userPwdEdit" name="userPwd"/></td>
		  				</tr>
		  			</table>
	 		 </form> 
	  		</div>
		
		<div id="mm" class="easyui-menu" style="width: 150px;">
			<div id="mm-tabupdate">
				刷新
			</div>
			<div class="menu-sep"></div>
			<div id="mm-tabclose">
				关闭
			</div>
			<div id="mm-tabcloseall">
				全部关闭
			</div>
			<div id="mm-tabcloseother">
				除此之外全部关闭
			</div>
			<div class="menu-sep"></div>
			<div id="mm-tabcloseright">
				当前页右侧全部关闭
			</div>
			<div id="mm-tabcloseleft">
				当前页左侧全部关闭
			</div>
			<div class="menu-sep"></div>
			<div id="mm-exit">
				退出
			</div>
		</div>
	</body>
</html>

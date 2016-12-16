<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="./head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv=content-type content="text/html; charset=utf-8">
<title>欢迎页面</title>

</head>
<body>
<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>
  <tr>
    <td bgcolor=#b1ceef height=1></td></tr>
  <tr height=20>
    <td background=images/shadow_bg.jpg></td></tr></table>
<table cellspacing=0 cellpadding=0 width="90%" align=center border=0>
  <tr height=100>
    <td align=middle width=100><img height=100 src="${path}/easyuiTemplate/images/admin_p.gif" 
      width=90></td>
    <td width=60>&nbsp;</td>
    <td>
      <table height=100 cellspacing=0 cellpadding=0 width="100%" border=0>
        
        <tr>
          <td>当前时间：<span id="thisDate"></span></td></tr>
        <tr>
          <td style="font-weight: bold; font-size: 16px"><%=session.getAttribute("displayName")%></td></tr>
        <tr>
          <td>欢迎进入lieeGin管理中心！</td></tr></table></td></tr>
  <tr>
    <td colspan=3 height=10></td></tr></table>
		<table cellspacing=0 cellpadding=0 width="95%" align=center border=0>
		  <tr height=20>
		    <td></td>
		  </tr>
		  <tr height=22>
		    <td style="padding-left: 20px; font-weight: bold; color: #ffffff" 
		    align=middle background=${path}/easyuiTemplate/images/title_bg2.jpg>您的相关信息</td>
		  </tr>
		  <tr bgcolor=#ecf4fc height=12>
		    <td></td>
		  </tr>
		  <tr height=20>
		    <td></td>
		  </tr>
		 </table>
		<table cellspacing=0 cellpadding=2 width="95%" align=center border=0>
		  <tr>
		    <td align=right width=100>登陆账号：</td>
		    <td style="color: #880000"><%=session.getAttribute("userName") %></td></tr>
		  <tr>
		    <td align=right>真实姓名：</td>
		    <td style="color: #880000"><%=session.getAttribute("displayName") %></td></tr>
		</table>
		<script type="text/javascript">
			$(function(){
				var	today=new Date();
				function date(){
					this.length=date.arguments.length;
					for(var i=0;i<this.length;i++)
						this[i+1]=date.arguments; 
				}
				var d=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
				var year = today.getFullYear();
				var month = today.getMonth();
				var day = today.getDate();
				var index = today.getDay();
				var weekday = d[index];
				$('#thisDate').html(year+"年"+month+"月"+day+"日"+" "+weekday);
			});
		</script>
 	 </body>
 </html>
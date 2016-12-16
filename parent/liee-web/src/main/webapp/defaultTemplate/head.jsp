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
  <link rel="stylesheet" href="${path}/defaultTemplate/css/style.css">
  
  <!-- jQuery 2.2.3 -->
<script src="${path}/defaultTemplate/plugin/AdminLTE-2.3.6/plugins/jQuery/jquery-2.2.3.min.js"></script>
  <script>
	$(document).ajaxComplete(function(event, xhr, settings) {
	    if(xhr.getResponseHeader("sessionstatus")=="timeOut"){
	        if(xhr.getResponseHeader("loginPath")){
	          //  alert("会话过期，请重新登陆!");
	            window.parent.location.href=xhr.getResponseHeader("loginPath");
	        }else{  
	        	  parent.BootstrapDialog.alert('请求超时请重新登陆 !');
	          //  alert("");  
	        }  
	    }  
	});  
  </script>
</head>
<body>
</body>
</html>

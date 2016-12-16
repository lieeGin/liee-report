<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="./head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>lieeGin登录页面</title>
		<meta http-equiv=content-type content="text/html; charset=utf-8">
		<style>
			html, body {
			   height: 100%;
			} 
		</style>
		<script type="text/javascript">
			if (window != top) 
			top.location.href = location.href; 
		</script>
		<script type="text/javascript">
		var path='<%=path%>';
		
		function login(){
				    var userId = $('#userId').val();
					var userPwd = $('#userPwd').val();
					var type = $('#type').val();
					if(""!=userId && ""!=userPwd){
						$.ajax({  
				        		type: "post",  
				        		url: path+"/admin/userLogin",                        
				        		data: {'userName':userId,'password':userPwd},
				        		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
				        		dataType:"json",                          
				        		success: function(data){
				     	   			if(data.success){
				     	   				location.href=path+"/admin/index";
				     	   			}else{
				     	   				alert(data.errMsg);
				  	   				}
				     			}
				  	     }); 	
					}else{
						alert("用户名或密码不能为空");
					}
			}
		
			$(function(){
				$('#userPwd').live("keyup",function(e){
					if (e.keyCode == 13) {
			  		  login();
					}
				});
				$('#loginBtn').unbind().bind('click',function(){
					login();
				});
				$('#userType').live('click',function(){
					$('#type').val(0);
				});
			});
		</script>
	</head>
	<body style="height:100% ">
	
		<table height="100%" cellspacing=0 cellpadding=0 width="100%"
			 border=0 >
			<tr>
				<td align=right valign="top" height="30">
					<!-- <input type="button" id="userType" value="管理员登录"/> -->
					<input type="hidden" id="type" name="type" value="0">
					<!--  <select id="type" name="type" style="width:170px;"><option value="1">渠道用户</option><option value="0">桌球联盟用户</option></select>-->
				</td>
			</tr>
			<tr>
				<td align=center>
					<table cellspacing=0 cellpadding=0 width=468 border=0>
						<tr>
							<td>
								 <img height=23 src="${path}/easyuiTemplate/images/login_1.jpg" width=468>
							</td>
						</tr>
						<tr>
							<td>
 								 <img height=147 src="${path}/easyuiTemplate/images/login_2.jpg" width=468>
							</td>
						</tr>
					</table>
					<table cellspacing=0 cellpadding=0 width=468 bgcolor=#ffffff
						border=0>
						<tr>
							<td width=16>
								<img height=122 src="${path}/easyuiTemplate/images/login_3.jpg" width=16>
							</td>
							<td align="center">
								<table cellspacing=0 cellpadding=2 width=270 border=0>
										<tr height=5>
											<td width=5px></td>
											<td  style="width:56px"></td>
											<td></td>
										</tr>
										<tr height=36>
											<td>&nbsp;</td>
											<td align="right">
												用户
											</td>
											<td>
												<input style="border-right: #000000 1px solid; border-top: #000000 1px solid; border-left: #000000 1px solid; border-bottom: #000000 1px solid"
													maxlength=30 size=24 width="120px" value="" id="userId" name="userId">
											</td>
										</tr>
										<tr height=36>
											<td>
												&nbsp;
											</td>
											<td align="right" >
												密码
											</td>
											<td>
												<input
													style="border-right: #000000 1px solid; border-top: #000000 1px solid; border-left: #000000 1px solid; border-bottom: #000000 1px solid"
													type=password maxlength=30 width="120px" size=24 value="" id="userPwd"
													name="userPwd">
											</td>
										</tr>
										<tr height=5>
											<td colspan=3></td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												<%-- <input id="loginBtn" type=image height=18 width=70
													src="${path}/easyuiTemplate/images/bt_login.gif"> --%>
												<a href="#" id="loginBtn" class="easyui-linkbutton" >登录</a>
											</td>
										</tr>
								</table>
							</td>
							<td width=16>
								 <img height=122 src="${path}/easyuiTemplate/images/login_4.jpg" width=16>
							</td>
						</tr>
					</table>
					<table cellspacing=0 cellpadding=0 width=468 border=0>
						<tr>
							<td>
								<img height=16 src="${path}/easyuiTemplate/images/login_5.jpg" width=468>
							</td>
						</tr>
					</table>
					<table cellspacing=0 cellpadding=0 width=468 border=0>
						<tr>
							<td align=right>
								<a href="#" target=_blank>
								    <%-- <img height=26 src="${path}/easyuiTemplate/images/login_6.gif" width=165 border=0> --%>
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>

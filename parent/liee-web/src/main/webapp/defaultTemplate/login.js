$(function(){
	
	$("#signIn").click(function(){
		
		var userName = $("#userName").val();
		var password = $("#password").val();
		
		if(!userName || !password){
			$("#errmsg").html("请输入用户名和密码");
			return ;
		}
		
		$.ajax({  
       		type: "post",  
       		url: path + "/admin/userLogin",
       		data : {"userName":userName,"password":password },
       		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
       		dataType:"json",                          
       		success: function(data){
       			if(data.success){
       				$("#errmsg").html("");
       				location.href=basePath+"admin/index"
       			}else{
       				$("#errmsg").html(data.errMsg);
       			}
       		}
		 });
		
	});
	
})
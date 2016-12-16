 $(function(){
			InitLeftMenu();
			tabClose();
			tabCloseEven();
			$('#loginOut').live("click",function(){
				$.ajax({  
	        		type: "post",  
	        		url: sysUtil.bp()+"/admin/logout",                        
	        		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
	        		dataType:"json",                          
	        		success: function(data){
	        			if(data && data.success){
	        				location.href=sysUtil.bp()+"/admin/login";
	        			}
	        		}
				});
			});
		        var pwdDialog =  $("#pwdDialog").show().dialog({
		        	 modal:true,closed:true,
		        	 buttons:[{
		        	 		text:"保存",
		        	 		handler:function(){
		        	 			$.ajax({  
		        	        		type: "post",  
		        	        		url: sysUtil.bp()+"/user/updateUserPwd",  
		        	        		data:$('#userUpdatePwdForm').serialize(),                  
		        	        		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		        	        		dataType:"json",                          
		        	        		success: function(data){
		        				    	if(data.success){
		        		    				$.messager.alert('提示','您的密码已修改，请牢记!');  
		        				    	}else{
		        				    		$.messager.alert('提示',data.errMsg);  
		        				    	}
		        	        		}	
		        	  	    	 }); 
		        	 			$("#userUpdatePwdForm")[0].reset();
		        	 			pwdDialog.dialog("close");
	        	 			}
		        	 	 },{
		        	 		text:"取消",
		        	 		handler:function(){
		        	 			$("#userUpdatePwdForm")[0].reset();
		        	 			pwdDialog.dialog("close");
	        	 			}
	 				}]
		        });
			
			$('#updatePwd').live("click",function(){
				pwdDialog.dialog("open");
			});
		});

		//初始化左侧
		function InitLeftMenu() {
			$("#nav").accordion({animate:false});
			$.ajax({  
        		type: "post",  
        		url: sysUtil.bp()+"/menu/getMenu",                        
        		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
        		dataType:"json",                          
        		success: function(data){
        			if(!data){
        				return;
        			}
        			var menus = data;
        			for(var i = 0; i<menus.length; i++){
        				var menulist = menus[i]['subMenuList'];
        				var parentManuName = menus[i]['parentMenuName'];
        				if(menulist.length>0){
        					var str='<ul>';
        					for(var j=0;j< menulist.length; j++){
        						str += '<li><div><a ref="'+menulist[j]["menuId"]+'" href="javascript:;" rel="'+sysUtil.bp()+'/easyuiTemplate/' + menulist[j]["url"] +'?menuId='+menulist[j]["menuId"]+'&authLevel='+menulist[j]["authLevel"]+ '" ><span class="nav">' + menulist[j]["menuName"] + '</span></a></div></li> ';
        					}
        					str += '</ul>';
        					$('#nav').accordion('add', {
        			            title: parentManuName,//moduleGroup["groupName"],
        			            content: str,
        			            iconCls: 'icon icon-nav'
        			        });
        				}
        			}
        			 $('.easyui-accordion li a').click(function(){
     					var tabTitle = $(this).children('.nav').text();
     					var url = $(this).attr("rel");
     					var menuid = $(this).attr("ref");
     					var icon = '';//icon-save
     					addTab(tabTitle,url,icon);
     					$('.easyui-accordion li div').removeClass("selected");
     					$(this).parent().addClass("selected");
     				}).hover(function(){
     					$(this).parent().addClass("hover");
     				},function(){
     					$(this).parent().removeClass("hover");
     				});

     				//选中第一个
     				/*var panels = $('#nav').accordion('panels');
     				var t = panels[0].panel('options').title;
     			    $('#nav').accordion('select', t);*/
     			  
     			    $('#tabs').tabs('add',{  
     				    title:'主页',  
     				    href:sysUtil.bp()+'/easyuiTemplate/welcome.jsp',
     				    width:'100',  
     				    closable:false 
     				});
        		}	
  	    	 }); 
		}
		//获取左侧导航的图标
		function getIcon(menuid){
			var icon = 'icon ';
			$.each(_menus.menus, function(i, n) {
				 $.each(n.menus, function(j, o) {
				 	if(o.menuid==menuid){
						icon += o.icon;
					}
				 });
			});

			return icon;
		}
		var cur = {};
		function addTab(subtitle,url,icon){
			if(!$('#tabs').tabs('exists',subtitle)){
				$('#tabs').tabs('add',{
					title:subtitle,
					content:createFrame(url),
					closable:true,
					icon:icon
				});
				cur[subtitle] = new Date().getTime()/1000;
			}else{
				var curTime = new Date().getTime()/1000;
				if(curTime-cur[subtitle]>2){
					$('#tabs').tabs('select',subtitle);
					$('#mm-tabupdate').click();
					cur[subtitle] = new Date().getTime()/1000;
				}
			}
			tabClose();
		}

		function createFrame(url)
		{
			var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			return s;
		}

		function tabClose()
		{
			/*双击关闭TAB选项卡*/
			$(".tabs-inner").dblclick(function(){
				var subtitle = $(this).children(".tabs-closable").text();
				$('#tabs').tabs('close',subtitle);
			});
			/*为选项卡绑定右键*/
			$(".tabs-inner").bind('contextmenu',function(e){
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				});

				var subtitle =$(this).children(".tabs-closable").text();

				$('#mm').data("currtab",subtitle);
				$('#tabs').tabs('select',subtitle);
				return false;
			});
		}
		//绑定右键菜单事件
		function tabCloseEven()
		{
			//刷新
			$('#mm-tabupdate').click(function(){
				var currTab = $('#tabs').tabs('getSelected');
				var url = $(currTab.panel('options').content).attr('src');
				$('#tabs').tabs('update',{
					tab:currTab,
					options:{
						content:createFrame(url)
					}
				});
			});
			//关闭当前
			$('#mm-tabclose').click(function(){
				var currtab_title = $('#mm').data("currtab");
				$('#tabs').tabs('close',currtab_title);
			});
			//全部关闭
			$('#mm-tabcloseall').click(function(){
				$('.tabs-inner span').each(function(i,n){
					var t = $(n).text();
					$('#tabs').tabs('close',t);
				});
			});
			//关闭除当前之外的TAB
			$('#mm-tabcloseother').click(function(){
				$('#mm-tabcloseright').click();
				$('#mm-tabcloseleft').click();
			});
			//关闭当前右侧的TAB
			$('#mm-tabcloseright').click(function(){
				var nextall = $('.tabs-selected').nextAll();
				if(nextall.length==0){
					//msgShow('系统提示','后边没有啦~~','error');
					alert('后边没有啦~~');
					return false;
				}
				nextall.each(function(i,n){
					var t=$('a:eq(0) span',$(n)).text();
					$('#tabs').tabs('close',t);
				});
				return false;
			});
			//关闭当前左侧的TAB
			$('#mm-tabcloseleft').click(function(){
				var prevall = $('.tabs-selected').prevAll();
				if(prevall.length==0){
					alert('到头了，前边没有啦~~');
					return false;
				}
				prevall.each(function(i,n){
					var t=$('a:eq(0) span',$(n)).text();
					$('#tabs').tabs('close',t);
				});
				return false;
			});

			//退出
			$("#mm-exit").click(function(){
				$('#mm').menu('hide');
			});
			
		}

		//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
		function msgShow(title, msgString, msgType) {
			$.messager.alert(title, msgString, msgType);
		}

-- 初始化数据
insert into sys_user(`user_name`,`display_name`,`sex`,`password`,`mobile_phone`,`email`,`register_time`)  
values ('admin','超级管理员',1,'e10adc3949ba59abbe56e057f20f883e','13510086407','lieeking@qq.com',now());

-- 默认的前台模版
INSERT INTO `systemconfig` (`id`, `keyword`, `valuestr`) VALUES ('1', 'template_name', 'easyui');

-- 初始化角色
INSERT INTO  `sys_role` (`id`, `role_name`, `remark`) VALUES ('1', '超级管理员', '拥有所有权限');

-- 初始化菜单
INSERT INTO  `sys_menu` (`id`, `menu_name`, `parent_id`, `level`, `url`, `display_order`) VALUES ('1', '系统管理', '0', '1', '', '99');
INSERT INTO  `sys_menu` (`id`, `menu_name`, `parent_id`, `level`, `url`, `display_order`) VALUES ('2', '用户管理', '1', '2', 'system/user/sysUser.jsp', '1');
INSERT INTO  `sys_menu` (`id`, `menu_name`, `parent_id`, `level`, `url`, `display_order`) VALUES ('3', '角色管理', '1', '2', 'system/roleauth/sysRole.jsp', '2');
INSERT INTO  `sys_menu` (`id`, `menu_name`, `parent_id`, `level`, `url`, `display_order`) VALUES ('4', '菜单管理', '1', '2', 'system/menu/sysMenu.jsp', '3');
INSERT INTO  `sys_menu` (`id`, `menu_name`, `parent_id`, `level`, `url`, `display_order`) VALUES ('8', 'PGC', '0', '1', '', '1');
INSERT INTO  `sys_menu` (`id`, `menu_name`, `parent_id`, `level`, `url`, `display_order`) VALUES ('9', '系统参数', '1', '2', 'system/systemconfig/systemconfig.jsp', '4');
INSERT INTO  `sys_menu` (`id`, `menu_name`, `parent_id`, `level`, `url`, `display_order`) VALUES ('10', '缓存管理', '1', '2', 'system/memery/memeryManage.jsp', '5');

-- 初始化角色菜单关系
INSERT INTO  `sys_role_menu` ( `role_id`, `menu_id`, `auth_level`) VALUES ( '1', '1', '2');
INSERT INTO  `sys_role_menu` ( `role_id`, `menu_id`, `auth_level`) VALUES ( '1', '2', '2');
INSERT INTO  `sys_role_menu` ( `role_id`, `menu_id`, `auth_level`) VALUES ( '1', '3', '2');
INSERT INTO  `sys_role_menu` ( `role_id`, `menu_id`, `auth_level`) VALUES ( '1', '4', '2');
INSERT INTO  `sys_role_menu` ( `role_id`, `menu_id`, `auth_level`) VALUES ( '1', '8', '2');
INSERT INTO  `sys_role_menu` ( `role_id`, `menu_id`, `auth_level`) VALUES ( '1', '9', '2');
INSERT INTO  `sys_role_menu` ( `role_id`, `menu_id`, `auth_level`) VALUES ( '1', '10', '2');

-- 初始化用户角色关系
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES ('1', '1', '1');


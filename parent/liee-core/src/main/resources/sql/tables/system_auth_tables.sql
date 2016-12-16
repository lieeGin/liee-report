/**
  系统角色
**/
CREATE TABLE `sys_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_name` VARCHAR(20) NOT NULL COMMENT '角色名称',
  `remark` VARCHAR(255) NOT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='系统角色';


/**
  用户和角色关系
**/
CREATE TABLE `sys_user_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` INT(11) NOT NULL COMMENT '用户ID',
  `role_id` INT(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='系统用户角色关系';



/**
  系统角色权限关系
**/
CREATE TABLE `sys_role_menu` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` INT(11) NOT NULL COMMENT '角色ID',
  `menu_id` INT(11) NOT NULL COMMENT '菜单ID',
  `auth_level` TINYINT(2) NOT NULL COMMENT '权限等级（1:只读   2：读写）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='系统角色权限关系';




/**
  系统用户表
**/
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_name` VARCHAR(20) NOT NULL COMMENT '用户名',
  `display_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '显示名称',
  `sex` TINYINT(2) NOT NULL DEFAULT 0 COMMENT '性别,0:保密  1:男  2:女',
  `password` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '登录密码',
  `mobile_phone` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '手机号码',
  `email` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `register_time` DATETIME COMMENT '注册时间',
  `last_login_time` DATETIME COMMENT '最后登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='系统用户表'
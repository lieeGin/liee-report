/**
  系统菜单
**/
CREATE TABLE `sys_menu` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `menu_name` VARCHAR(20) NOT NULL COMMENT '菜单名称',
  `parent_id` INT(11) NOT NULL DEFAULT 0 COMMENT '父菜单ID',
  `level` INT(2) NOT NULL DEFAULT 0 COMMENT '层级',
  `url` VARCHAR(100) NOT NULL COMMENT '菜单url',
  `display_order` INT(2) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='系统菜单';
/**
 * 系统数据字典
 */
CREATE TABLE `sys_code` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT '字典编号',
  `text` varchar(255) NOT NULL DEFAULT '' COMMENT '字典文本',
  `parent_code` varchar(64) NOT NULL DEFAULT '' COMMENT '父编号',
  `sort` INT(2) NOT NULL DEFAULT 0 COMMENT '层级',
  `display_order` INT(2) NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `syscode_key` (`code`,`parent_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
  系统参数配置
**/
-- CREATE TABLE `sys_config` (
--  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
--  `param_code` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '参数名称',
--  `param_value` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '参数值',
--  PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT='系统参数';

CREATE TABLE `systemconfig` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(64) NOT NULL DEFAULT '' COMMENT '键',
  `valuestr` varchar(255) NOT NULL DEFAULT '' COMMENT '值',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sc_key` (`keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
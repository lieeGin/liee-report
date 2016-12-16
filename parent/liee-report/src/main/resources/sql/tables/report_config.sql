CREATE TABLE `rep_report` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '报表名称',
  `code` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '报表编号(唯一)',
  `data_source` INT(10) NOT NULL DEFAULT 0 COMMENT '报表数据来源 0 groovy查询  1 程序接口 ',
  `api_address` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '调用接口地址 数据来源是程序接口时使用',
  `groovy_file` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '数据查询groovy 数据来源是groovy查询时使用',
  `report_show_way` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '报表展示模式 (表格 0 ,柱状图 1 , 混合1,2等)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义报表头';


CREATE TABLE `rep_report_param` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `report_id` INT(10) NOT NULL COMMENT '所属报表ID',
  `name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '参数名称',
  `code` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '参数key',
  `type` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '参数数据类型(number,text等)',
  `widget_type` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '该参数的控件类型(input,select,checkbox等)',
  `select_key` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '控件类型为select时，通过ajax后台加载数据字典的key值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义报表查询参数';



CREATE TABLE `rep_report_column` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `report_id` INT(10) NOT NULL COMMENT '所属报表ID',
  `title` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '列名称',
  `field` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '列的key',
  `sortable` INT(2) NOT NULL DEFAULT 0 COMMENT '是否需要允许排序',
  `formatter` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '列格式化',
  `width` INT(10) NOT NULL DEFAULT 0 COMMENT '列宽',
  `align` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '对齐方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义报表结果列';



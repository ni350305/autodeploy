-- --------------------------------------------------------
-- Host:                         192.168.1.254
-- Server version:               5.5.21-log - Source distribution
-- Server OS:                    Linux
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2013-12-19 10:18:10
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping structure for table summall.easy_account
DROP TABLE IF EXISTS `easy_account`;
CREATE TABLE IF NOT EXISTS `easy_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `version` bigint(20) NOT NULL COMMENT 'id',
  `createTime` datetime DEFAULT NULL COMMENT 'id',
  `createBy` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'id',
  `lastModifyTime` datetime DEFAULT NULL COMMENT 'id',
  `lastModifyBy` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `passwd` varchar(1000) CHARACTER SET utf8 NOT NULL COMMENT '密码md5加密',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户表';

-- Data exporting was unselected.


-- Dumping structure for table summall.easy_deploy_command
DROP TABLE IF EXISTS `easy_deploy_command`;
CREATE TABLE IF NOT EXISTS `easy_deploy_command` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `version` bigint(20) NOT NULL COMMENT 'id',
  `createTime` datetime DEFAULT NULL COMMENT 'id',
  `createBy` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'id',
  `lastModifyTime` datetime DEFAULT NULL COMMENT 'id',
  `lastModifyBy` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '命令别名',
  `command` varchar(1000) CHARACTER SET utf8 NOT NULL COMMENT '可执行的远程命令',
  `projectId` bigint(20) NOT NULL COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='部署命令表';

-- Data exporting was unselected.


-- Dumping structure for table summall.easy_deploy_file
DROP TABLE IF EXISTS `easy_deploy_file`;
CREATE TABLE IF NOT EXISTS `easy_deploy_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `version` bigint(20) NOT NULL COMMENT 'id',
  `createTime` datetime DEFAULT NULL COMMENT 'id',
  `createBy` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'id',
  `lastModifyTime` datetime DEFAULT NULL COMMENT 'id',
  `lastModifyBy` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '部署文件别名',
  `deployPath` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '部署到目标服务器路径',
  `fileName` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '文件名',
  `projectId` bigint(20) NOT NULL COMMENT '项目id',
  `isEmptyFolder` int(1) NOT NULL DEFAULT '1' COMMENT '部署前是否清空文件夹',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='部署文件表';

-- Data exporting was unselected.


-- Dumping structure for table summall.easy_deploy_project
DROP TABLE IF EXISTS `easy_deploy_project`;
CREATE TABLE IF NOT EXISTS `easy_deploy_project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `version` bigint(20) NOT NULL COMMENT 'id',
  `createTime` datetime DEFAULT NULL COMMENT 'id',
  `createBy` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'id',
  `lastModifyTime` datetime DEFAULT NULL COMMENT 'id',
  `lastModifyBy` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'id',
  `name` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '别名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='部署项目表';

-- Data exporting was unselected.


-- Dumping structure for table summall.easy_deploy_task
DROP TABLE IF EXISTS `easy_deploy_task`;
CREATE TABLE IF NOT EXISTS `easy_deploy_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `version` bigint(20) NOT NULL COMMENT 'id',
  `createTime` datetime DEFAULT NULL COMMENT 'id',
  `createBy` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'id',
  `lastModifyTime` datetime DEFAULT NULL COMMENT 'id',
  `lastModifyBy` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '部署任务名称',
  `isConcurrence` tinyint(50) NOT NULL COMMENT '是否并行部署(是/否)(1/0)',
  `serverGroupId` bigint(50) NOT NULL COMMENT '服务器组id',
  `projectId` bigint(50) NOT NULL COMMENT '项目id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='部署任务表';

-- Data exporting was unselected.


-- Dumping structure for table summall.easy_deploy_task_file_command_relation
DROP TABLE IF EXISTS `easy_deploy_task_file_command_relation`;
CREATE TABLE IF NOT EXISTS `easy_deploy_task_file_command_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `version` bigint(20) NOT NULL COMMENT 'id',
  `createTime` datetime DEFAULT NULL COMMENT 'id',
  `createBy` varchar(50) DEFAULT NULL COMMENT 'id',
  `lastModifyTime` datetime DEFAULT NULL COMMENT 'id',
  `lastModifyBy` varchar(50) DEFAULT NULL COMMENT 'id',
  `deployTaskId` bigint(20) NOT NULL COMMENT '配置任务id',
  `deployFileOrCommandId` bigint(20) unsigned NOT NULL COMMENT '文件命令id',
  `deployType` tinyint(4) NOT NULL COMMENT '类型（文件/命令)(0/1)',
  `deployIndex` smallint(6) NOT NULL COMMENT '序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='部署任务与部署文件、部署命令关系表';

-- Data exporting was unselected.


-- Dumping structure for table summall.easy_server
DROP TABLE IF EXISTS `easy_server`;
CREATE TABLE IF NOT EXISTS `easy_server` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `version` bigint(20) NOT NULL DEFAULT '1',
  `createTime` datetime DEFAULT NULL,
  `createBy` varchar(50) DEFAULT NULL,
  `lastModifyTime` datetime DEFAULT NULL,
  `lastModifyBy` varchar(50) DEFAULT NULL,
  `ip` varchar(15) CHARACTER SET utf8 NOT NULL COMMENT 'ip',
  `port` int(5) NOT NULL COMMENT '端口',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '服务器别名',
  `userName` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '服务器登录用户名',
  `authType` tinyint(50) NOT NULL COMMENT '服务器证方式（密码/密钥)(0/1)',
  `comment` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index 2` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='服务器表';

-- Data exporting was unselected.


-- Dumping structure for table summall.easy_server_group
DROP TABLE IF EXISTS `easy_server_group`;
CREATE TABLE IF NOT EXISTS `easy_server_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `version` bigint(20) NOT NULL DEFAULT '1' COMMENT 'id',
  `createTime` datetime DEFAULT NULL COMMENT 'id',
  `createBy` varchar(50) DEFAULT NULL COMMENT 'id',
  `lastModifyTime` datetime DEFAULT NULL COMMENT 'id',
  `lastModifyBy` varchar(50) DEFAULT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 NOT NULL COMMENT '服务器组名',
  `comment` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='服务器组表';

-- Data exporting was unselected.


-- Dumping structure for table summall.easy_server_group_relation
DROP TABLE IF EXISTS `easy_server_group_relation`;
CREATE TABLE IF NOT EXISTS `easy_server_group_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `version` bigint(20) DEFAULT NULL COMMENT 'id',
  `createTime` datetime DEFAULT NULL COMMENT 'id',
  `createBy` varchar(50) DEFAULT NULL COMMENT 'id',
  `lastModifyTime` datetime DEFAULT NULL COMMENT 'id',
  `lastModifyBy` varchar(50) DEFAULT NULL COMMENT 'id',
  `serverGroupId` bigint(20) NOT NULL COMMENT '服务器组id',
  `serverId` bigint(20) NOT NULL COMMENT '服务器id',
  PRIMARY KEY (`id`),
  KEY `serverGroupId` (`serverGroupId`),
  KEY `serverId` (`serverId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='服务器 与服务器组 关系表';

-- Data exporting was unselected.
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

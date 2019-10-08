/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : atm

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2019-10-08 11:01:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cardinfo
-- ----------------------------
DROP TABLE IF EXISTS `cardinfo`;
CREATE TABLE `cardinfo` (
  `card_id` varchar(50) NOT NULL COMMENT '银行的卡号规则和电话号码一样，一般前8位代表特殊含义，如某总行某支行等。假定该行要求其营业厅的卡号格式为：1010 3576 xxxx xxx开始,每4位号码后有空格，卡号一般是随机产生',
  `cur_type` varchar(10) NOT NULL DEFAULT 'RMB' COMMENT '默认为RMB\r\n            ',
  `saving_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0活期1/定期\r\n            ',
  `money` decimal(30,0) NOT NULL COMMENT '不低于1元\r\n            ',
  `prestore` int(3) NOT NULL COMMENT '不低于1元,否则将销户\r\n            ',
  `password` varchar(6) NOT NULL DEFAULT '123456' COMMENT '6位数字，开户时默认为6个“8”\r\n            ',
  `open_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '默认为系统当前日期\r\n            ',
  `is_report_loss` tinyint(2) DEFAULT '0',
  `customer_id` int(11) DEFAULT NULL COMMENT '自动编号 从1开始',
  PRIMARY KEY (`card_id`),
  KEY `FK_User_Black` (`customer_id`),
  CONSTRAINT `FK_User_Black` FOREIGN KEY (`customer_id`) REFERENCES `userinfo` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cardinfo
-- ----------------------------
INSERT INTO `cardinfo` VALUES ('6100700240001078666', 'RMB', '0', '10800', '10', '666666', '2019-07-18 12:17:19', '0', '2');
INSERT INTO `cardinfo` VALUES ('6222600260001072444', 'RMB', '0', '1000', '10', '123456', '2019-07-18 12:16:23', '0', '1');

-- ----------------------------
-- Table structure for systemlog
-- ----------------------------
DROP TABLE IF EXISTS `systemlog`;
CREATE TABLE `systemlog` (
  `log_id` int(30) NOT NULL AUTO_INCREMENT COMMENT '日志记录行id',
  `card_id` varchar(30) DEFAULT NULL COMMENT '银行卡号',
  `function` varchar(200) NOT NULL COMMENT '操作方法',
  `params` varchar(800) DEFAULT NULL COMMENT '参数',
  `optime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '默认为系统当前日期',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=487 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemlog
-- ----------------------------
INSERT INTO `systemlog` VALUES ('483', '\"324\"', 'co.fengfeng.service.impl.LoginServiceImpl:getUserInfo', '\"324\"{\"session\":{\"attributes\":{},\"creationTime\":1563955010167,\"id\":\"D06E80936990BA31BB68B03A22EEA563\",\"lastAccessedTime\":1563955010167,\"maxInactiveInterval\":1800,\"isNew\":true,\"isValid\":true,\"thisAccessedTime\":1563955010169}}', '2019-07-24 15:56:50');
INSERT INTO `systemlog` VALUES ('484', '\"6100700240001078666\"', 'co.fengfeng.service.impl.LoginServiceImpl:getUserInfo', '\"6100700240001078666\"{\"session\":{\"attributes\":{\"cardId\":\"6100700240001078666\"},\"creationTime\":1563955010167,\"id\":\"D06E80936990BA31BB68B03A22EEA563\",\"lastAccessedTime\":1563955010380,\"maxInactiveInterval\":1800,\"isNew\":false,\"isValid\":true,\"thisAccessedTime\":1563955016131}}', '2019-07-24 15:56:56');
INSERT INTO `systemlog` VALUES ('485', '\"6100700240001078666\"', 'co.fengfeng.service.impl.LoginServiceImpl:getCardInfo', '\"6100700240001078666\"\"111111\"', '2019-07-24 15:57:22');
INSERT INTO `systemlog` VALUES ('486', '\"6100700240001078666\"', 'co.fengfeng.service.impl.MoneyServiceImpl:getUserMoney', '\"6100700240001078666\"', '2019-07-24 16:18:24');

-- ----------------------------
-- Table structure for transinfo
-- ----------------------------
DROP TABLE IF EXISTS `transinfo`;
CREATE TABLE `transinfo` (
  `card_id` varchar(50) DEFAULT NULL COMMENT '银行的卡号规则和电话号码一样，一般前8位代表特殊含义，如某总行某支行等。假定该行要求其营业厅的卡号格式为：1010 3576 xxxx xxx开始,每4位号码后有空格，卡号一般是随机产生',
  `trans_type` int(11) NOT NULL COMMENT '只能是存入/支取\r\n            ',
  `trans_money` int(11) NOT NULL COMMENT '大于0\r\n            ',
  `trans_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '默认为系统当前日期',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  KEY `FK_black_xinxi` (`card_id`),
  CONSTRAINT `FK_black_xinxi` FOREIGN KEY (`card_id`) REFERENCES `cardinfo` (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of transinfo
-- ----------------------------
INSERT INTO `transinfo` VALUES ('6100700240001078666', '1', '200', '2019-08-10 16:15:32', null);
INSERT INTO `transinfo` VALUES ('6100700240001078666', '2', '1000', '2019-08-10 16:15:58', '6100700240001078666');
INSERT INTO `transinfo` VALUES ('6100700240001078666', '0', '1000', '2019-08-10 16:16:11', null);

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自动编号 从1开始',
  `customer_name` varchar(10) NOT NULL COMMENT '开户人姓名',
  `pid` varchar(50) NOT NULL COMMENT '只能是18位或15位',
  `telephone` varchar(50) NOT NULL COMMENT '格式为xxxx-xxxxxxxx或手机号13位\r\n            ',
  `address` varchar(100) DEFAULT NULL COMMENT '客户地址',
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `AK_Key_2` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', '张三', '43052519991104351X', '15243909704', '湖南长沙');
INSERT INTO `userinfo` VALUES ('2', '李四', '420132198811042911', '13807042134', '湖南邵阳');

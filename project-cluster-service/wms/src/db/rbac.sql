/*
Navicat MySQL Data Transfer

Source Server         : localhost3306
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : cms_erp

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2020-04-06 18:31:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bus_customer
-- ----------------------------
DROP TABLE IF EXISTS `bus_customer`;
CREATE TABLE `bus_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customername` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `connectionperson` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_customer
-- ----------------------------
INSERT INTO `bus_customer` VALUES ('1', '小张超市', '111', '武汉', '027-9123131', '张大明', '13812312312321312', '中国银行', '654431331343413', '213123@sina.com', '430000', '1');
INSERT INTO `bus_customer` VALUES ('2', '小明超市', '222', '深圳', '0755-9123131', '张小明', '13812312312321312', '中国银行', '654431331343413', '213123@sina.com', '430000', '1');
INSERT INTO `bus_customer` VALUES ('3', '快七超市', '430000', '武汉', '027-11011011', '雷生', '13434134131', '招商银行', '6543123341334133', '6666@66.com', '545341', '1');
INSERT INTO `bus_customer` VALUES ('7', 'Eend 伊登', '331100', '北京海淀区', '18296557705', 'wwf', '242342', '2342', '4242', 'sf', '2342', '1');

-- ----------------------------
-- Table structure for bus_goods
-- ----------------------------
DROP TABLE IF EXISTS `bus_goods`;
CREATE TABLE `bus_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsname` varchar(255) DEFAULT NULL,
  `produceplace` varchar(255) DEFAULT NULL,
  `size` varchar(255) DEFAULT NULL,
  `goodspackage` varchar(255) DEFAULT NULL,
  `productcode` varchar(255) DEFAULT NULL,
  `promitcode` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `dangernum` int(11) DEFAULT NULL,
  `goodsimg` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_sq0btr2v2lq8gt8b4gb8tlk0i` (`providerid`) USING BTREE,
  CONSTRAINT `bus_goods_ibfk_1` FOREIGN KEY (`providerid`) REFERENCES `bus_provider` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_goods
-- ----------------------------
INSERT INTO `bus_goods` VALUES ('1', '娃哈哈', '武汉', '120ML', '瓶', 'PH12345', 'PZ1234', '小孩子都爱的', '2', '488', '10', '2020-04-03/mybatis.jpg', '1', '3');
INSERT INTO `bus_goods` VALUES ('2', '旺旺雪饼[小包]', '仙桃', '包', '袋', 'PH12312312', 'PZ12312', '好吃不上火', '4', '5280', '10', '2020-04-03/58E39C206C1A4D2EA97F0FA49261C479.jpg', '1', '1');
INSERT INTO `bus_goods` VALUES ('3', '旺旺大礼包', '仙桃', '盒', '盒', '11', '11', '111', '28', '883', '100', '2020-04-03/1FBFA45546C84FBFA21FC0F2276D3DC6.jpg', '1', '1');
INSERT INTO `bus_goods` VALUES ('4', '娃哈哈', '武汉', '200ML', '瓶', '11', '111', '12321', '3', '1100', '10', '2020-04-03/58E39C206C1A4D2EA97F0FA49261C479.jpg', '1', '3');
INSERT INTO `bus_goods` VALUES ('5', '娃哈哈', '武汉', '300ML', '瓶', '1234', '12321', '22221111', '3', '1000', '100', '2020-04-03/58E39C206C1A4D2EA97F0FA49261C479.jpg', '1', '3');
INSERT INTO `bus_goods` VALUES ('13', '盒纸巾', '江西', 'aadaf5', '盒', 'sf55', 'sdf', '日常用品', '3000', '3000', '100', '2020-04-03/mybatis.jpg', '1', '1');
INSERT INTO `bus_goods` VALUES ('14', '盒纸巾', '江西', 'efwerewr', '盒', 'sf55', 'sfs55', '日常用品', '3000', '1100', '100', '2020-04-03/mybatis.jpg', '1', '8');
INSERT INTO `bus_goods` VALUES ('16', '餐巾纸', '江西', 'sfd', '盒', 'sf55', 'sfs55', '日常用品', '16000', '100', '100', '2020-04-03/mybatis.jpg', '1', '8');
INSERT INTO `bus_goods` VALUES ('17', '盒纸巾', '江西', 'sfd', 'sf', 'sf55', 'sfs55', '日常用品', '3000', '2000', '100', '2020-04-03/mybatis.jpg', '1', '8');
INSERT INTO `bus_goods` VALUES ('18', 'sdfsdf', 'sfd', 'efwerewr', 'sf', 'sf55', 'sfs55', '日常用品', '3000', '2000', '100', '2020-04-03/mybatis.jpg', '1', '3');
INSERT INTO `bus_goods` VALUES ('21', '达利园', '江西', 'sfd', '盒', 'sf55', 'sdf', '干货', '16000', '2000', '100', '2020-04-03/mybatis.jpg', '1', '2');
INSERT INTO `bus_goods` VALUES ('23', '盒纸巾', 'sfd', 'sfd', '盒', 'sdf', 'sfs55', '日常用品', '3000', '2000', '100', '2020-04-03/mybatis.jpg', '1', '8');
INSERT INTO `bus_goods` VALUES ('25', '烧烤', '江西', 'sfd', '盒', 'sf55', 'sfs55', '日常用品', '3000', '2000', '100', '2020-04-03/mybatis.jpg', '1', '9');

-- ----------------------------
-- Table structure for bus_inport
-- ----------------------------
DROP TABLE IF EXISTS `bus_inport`;
CREATE TABLE `bus_inport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paytype` varchar(255) DEFAULT NULL,
  `inporttime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `inportprice` double DEFAULT NULL,
  `providerid` int(11) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_1o4bujsyd2kl4iqw48fie224v` (`providerid`) USING BTREE,
  KEY `FK_ho29poeu4o2dxu4rg1ammeaql` (`goodsid`) USING BTREE,
  CONSTRAINT `bus_inport_ibfk_1` FOREIGN KEY (`providerid`) REFERENCES `bus_provider` (`id`),
  CONSTRAINT `bus_inport_ibfk_2` FOREIGN KEY (`goodsid`) REFERENCES `bus_goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_inport
-- ----------------------------
INSERT INTO `bus_inport` VALUES ('1', '微信', '2020-04-02 00:00:00', '张三', '100', '备注', '3.5', '1', '1');
INSERT INTO `bus_inport` VALUES ('2', '支付宝', '2020-04-02 00:00:00', '张三', '1000', '无', '2.5', '3', '3');
INSERT INTO `bus_inport` VALUES ('3', '银联', '2020-04-02 00:00:00', '张三', '100', '1231', '111', '3', '3');
INSERT INTO `bus_inport` VALUES ('4', '银联', '2020-04-02 00:00:00', '张三', '1000', '无', '2', '3', '1');
INSERT INTO `bus_inport` VALUES ('5', '银联', '2020-04-02 00:00:00', '张三', '100', '无', '1', '3', '1');
INSERT INTO `bus_inport` VALUES ('6', '银联', '2020-04-02 00:00:00', '张三', '100', '1231', '2.5', '1', '2');
INSERT INTO `bus_inport` VALUES ('8', '支付宝', '2020-04-02 00:00:00', '张三', '100', '', '1', '3', '1');
INSERT INTO `bus_inport` VALUES ('10', '支付宝', '2020-04-02 17:17:57', '超级管理员', '100', 'sadfasfdsa', '1.5', '3', '1');
INSERT INTO `bus_inport` VALUES ('11', '支付宝', '2020-04-02 16:12:25', '超级管理员', '21', '21', '21', '1', '3');
INSERT INTO `bus_inport` VALUES ('12', '微信', '2020-04-02 16:48:24', '超级管理员', '100', '123213213', '12321321', '3', '1');
INSERT INTO `bus_inport` VALUES ('14', '微信', '2020-04-03 12:20:45', '超级管理员', '100', '222', '20', '1', '14');
INSERT INTO `bus_inport` VALUES ('16', '支付宝', '2020-04-03 13:18:24', '超级管理员', '100', '1111', '100', '1', '3');
INSERT INTO `bus_inport` VALUES ('17', '支付宝', '2020-04-03 13:30:58', '超级管理员', '1000', '进货', '1000', '8', '14');
INSERT INTO `bus_inport` VALUES ('21', '微信', '2020-04-04 08:59:47', '超级管理员', '111', '备注', '111', '8', '16');
INSERT INTO `bus_inport` VALUES ('22', '支付宝', '2020-04-04 10:41:36', '超级管理员', '100', '备注', '100', '1', '2');
INSERT INTO `bus_inport` VALUES ('23', '信用卡', '2020-04-04 10:42:27', '超级管理员', '2000', '备注', '200', '1', '13');

-- ----------------------------
-- Table structure for bus_outport
-- ----------------------------
DROP TABLE IF EXISTS `bus_outport`;
CREATE TABLE `bus_outport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `providerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `outputtime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `outportprice` double(10,2) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_outport
-- ----------------------------
INSERT INTO `bus_outport` VALUES ('7', '1', '银联', '2020-04-05 05:14:48', '超级管理员', '200.00', '200', '111', '13');
INSERT INTO `bus_outport` VALUES ('8', '1', '支付宝', '2020-04-05 05:15:13', '超级管理员', '100.00', '20', '111', '2');
INSERT INTO `bus_outport` VALUES ('9', '8', '微信', '2020-04-05 05:47:06', '超级管理员', '111.00', '11', '退货', '16');

-- ----------------------------
-- Table structure for bus_provider
-- ----------------------------
DROP TABLE IF EXISTS `bus_provider`;
CREATE TABLE `bus_provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `providername` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `connectionperson` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `bank` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_provider
-- ----------------------------
INSERT INTO `bus_provider` VALUES ('1', '旺旺食品', '434000', '仙桃', '0728-4124312', '小明', '13413441141', '中国农业银行', '654124345131', '12312321@sina.com', '5123123', '1');
INSERT INTO `bus_provider` VALUES ('2', '达利园食品', '430000', '汉川', '0728-4124312', '大明', '13413441141', '中国农业银行', '654124345131', '12312321@sina.com', '5123123', '1');
INSERT INTO `bus_provider` VALUES ('3', '娃哈哈集团', '513131', '杭州', '21312', '小晨', '12312', '建设银行', '512314141541', '123131', '312312', '1');
INSERT INTO `bus_provider` VALUES ('8', '纸巾集团', '331100', '北京海淀区', '18296557705', '24342342', '18296557705', '中国银行', '24323242342342', '243234', '2424234', '1');
INSERT INTO `bus_provider` VALUES ('9', '啊大苏打', '331100', '北京海淀区', '18296557705', '234', '35345', '5345', '3534535', '353453', '354354', '1');

-- ----------------------------
-- Table structure for bus_sales
-- ----------------------------
DROP TABLE IF EXISTS `bus_sales`;
CREATE TABLE `bus_sales` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `salestime` datetime DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `saleprice` decimal(10,2) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_sales
-- ----------------------------
INSERT INTO `bus_sales` VALUES ('4', '2', '微信', '2020-04-05 11:07:52', '超级管理员', '133', '很好', '233.00', '2');
INSERT INTO `bus_sales` VALUES ('5', '1', '支付宝', '2020-04-05 11:09:42', '超级管理员', '100', '备注', '122.00', '3');
INSERT INTO `bus_sales` VALUES ('6', '7', '支付宝', '2020-04-05 11:10:14', '超级管理员', '11', '', '11.00', '1');
INSERT INTO `bus_sales` VALUES ('7', '2', '支付宝', '2020-04-05 11:34:31', '超级管理员', '90', '不错哦', '20.00', '3');

-- ----------------------------
-- Table structure for bus_salesback
-- ----------------------------
DROP TABLE IF EXISTS `bus_salesback`;
CREATE TABLE `bus_salesback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) DEFAULT NULL,
  `paytype` varchar(255) DEFAULT NULL,
  `salesbacktime` datetime DEFAULT NULL,
  `salebackprice` double(10,2) DEFAULT NULL,
  `operateperson` varchar(255) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `goodsid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of bus_salesback
-- ----------------------------
INSERT INTO `bus_salesback` VALUES ('1', '2', '支付宝', '2020-04-05 12:04:49', '20.00', '超级管理员', '10', '退货', '3');
INSERT INTO `bus_salesback` VALUES ('2', '2', '支付宝', '2020-04-05 12:05:58', '20.00', '超级管理员', '20', '退货', '3');
INSERT INTO `bus_salesback` VALUES ('3', '2', '支付宝', '2020-04-05 12:08:26', '20.00', '超级管理员', '10', '退货哦', '3');
INSERT INTO `bus_salesback` VALUES ('4', '1', '支付宝', '2020-04-05 12:11:53', '122.00', '超级管理员', '22', '不好', '3');
INSERT INTO `bus_salesback` VALUES ('5', '2', '微信', '2020-04-05 12:18:18', '233.00', '超级管理员', '100', '有点不好吃', '2');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `open` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL COMMENT '状态【0不可用1可用】',
  `ordernum` int(11) DEFAULT NULL COMMENT '排序码【为了调事显示顺序】',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '总经办', '1', '大BOSS', '北京', '1', '1', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('2', '1', '销售部', '0', '程序员屌丝', '武汉', '1', '2', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('3', '1', '运营部', '0', '无', '武汉', '1', '3', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('4', '1', '生产部', '0', '无', '武汉', '1', '4', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('5', '2', '销售一部', '0', '销售一部', '武汉', '1', '5', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('6', '2', '销售二部', '0', '销售二部', '武汉', '1', '6', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('7', '3', '运营一部', '0', '运营一部', '武汉', '1', '7', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('8', '2', '销售三部', '0', '销售三部', '11', '1', '8', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('9', '2', '销售四部', '0', '销售四部', '222', '1', '9', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('10', '2', '销售五部', '0', '销售五部', '33', '1', '10', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('18', '4', '生产一部', '0', '生产食品', '武汉', '1', '11', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('34', '5', '生产3号', '1', '不错', '北京海淀区1', '1', '20', '2020-03-23 09:12:20');
INSERT INTO `sys_dept` VALUES ('35', '3', '生产3线', '1', '嗯嗯', '海南', '1', '21', '2020-03-23 09:12:20');

-- ----------------------------
-- Table structure for sys_loginfo
-- ----------------------------
DROP TABLE IF EXISTS `sys_loginfo`;
CREATE TABLE `sys_loginfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(255) DEFAULT NULL,
  `loginip` varchar(255) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_loginfo
-- ----------------------------
INSERT INTO `sys_loginfo` VALUES ('63', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('64', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('65', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('66', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('67', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('68', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('69', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('70', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('71', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('72', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('73', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('74', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('75', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('76', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('77', '刘八-lb', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('78', '刘八-lb', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('79', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('80', '刘八-lb', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('81', '刘八-lb', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('82', '刘八-lb', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('83', '刘八-lb', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('84', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('85', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('86', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('87', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('88', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('89', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('90', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('91', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('92', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('93', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('94', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('95', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('96', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('97', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('98', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('99', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('100', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('101', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('102', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('104', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('105', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('106', '李四-ls', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('107', '李四-ls', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('108', '孙七-sq', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('109', '刘八-lb', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('110', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('111', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('112', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('113', '李四-ls', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('114', '李四-ls', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('115', '李四-ls', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('116', '李四-ls', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('117', '李四-ls', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('118', '李四-ls', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('119', '李四-ls', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('120', '李四-ls', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('121', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('122', '李四-ls', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('123', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('124', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('125', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('126', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('127', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('128', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('129', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('130', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('131', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('132', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('133', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('134', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('135', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('136', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('138', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('139', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('140', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('141', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('142', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('143', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('173', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('174', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('175', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('176', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('177', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('178', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('179', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('181', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('182', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('183', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('184', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('185', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('186', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('187', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('188', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('189', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('190', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('191', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('192', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('193', '孙尚香-sunshangxiang', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('194', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('195', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('196', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('197', '小剑-xiaojian', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('198', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('199', '刘八-lb', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('200', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('201', '小剑-xiaojian', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('202', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('203', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('204', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('205', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('206', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('207', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('208', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('209', '刘八-lb', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('210', '超级管理员-system', '127.0.0.1', '2020-03-21 06:12:20');
INSERT INTO `sys_loginfo` VALUES ('211', '超级管理员-system', '127.0.0.1', '2020-03-31 09:30:25');
INSERT INTO `sys_loginfo` VALUES ('212', '超级管理员-system', '127.0.0.1', '2020-03-31 09:33:44');
INSERT INTO `sys_loginfo` VALUES ('213', '超级管理员-system', '127.0.0.1', '2020-03-31 09:34:46');
INSERT INTO `sys_loginfo` VALUES ('214', '超级管理员-system', '127.0.0.1', '2020-03-31 09:37:39');
INSERT INTO `sys_loginfo` VALUES ('215', '小剑-xiaojian', '127.0.0.1', '2020-03-31 10:30:18');
INSERT INTO `sys_loginfo` VALUES ('216', '超级管理员-system', '127.0.0.1', '2020-04-02 01:57:45');
INSERT INTO `sys_loginfo` VALUES ('217', '超级管理员-system', '127.0.0.1', '2020-04-02 05:09:40');
INSERT INTO `sys_loginfo` VALUES ('218', '超级管理员-system', '127.0.0.1', '2020-04-02 07:44:38');
INSERT INTO `sys_loginfo` VALUES ('219', '超级管理员-system', '127.0.0.1', '2020-04-02 07:45:07');
INSERT INTO `sys_loginfo` VALUES ('220', '超级管理员-system', '127.0.0.1', '2020-04-03 01:46:16');
INSERT INTO `sys_loginfo` VALUES ('221', '超级管理员-system', '127.0.0.1', '2020-04-03 02:33:44');
INSERT INTO `sys_loginfo` VALUES ('222', '超级管理员-system', '127.0.0.1', '2020-04-04 08:22:22');
INSERT INTO `sys_loginfo` VALUES ('223', '超级管理员-system', '127.0.0.1', '2020-04-04 09:47:11');
INSERT INTO `sys_loginfo` VALUES ('224', '超级管理员-system', '127.0.0.1', '2020-04-04 11:01:30');
INSERT INTO `sys_loginfo` VALUES ('225', '超级管理员-system', '127.0.0.1', '2020-04-05 04:56:37');
INSERT INTO `sys_loginfo` VALUES ('226', '超级管理员-system', '127.0.0.1', '2020-04-05 05:41:11');
INSERT INTO `sys_loginfo` VALUES ('227', '超级管理员-system', '127.0.0.1', '2020-04-05 10:09:34');
INSERT INTO `sys_loginfo` VALUES ('228', '超级管理员-system', '127.0.0.1', '2020-04-05 10:35:02');
INSERT INTO `sys_loginfo` VALUES ('229', '超级管理员-system', '127.0.0.1', '2020-04-05 10:35:49');

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `content` text,
  `createtime` datetime DEFAULT NULL,
  `opername` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES ('1', '关于系统V1.3更新公告', '2', '2020-03-23 09:12:20', '管理员');
INSERT INTO `sys_notice` VALUES ('11', '关于系统V1.1更新公告', '21321321321<img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\">', '2020-03-23 09:12:20', '超级管理员');
INSERT INTO `sys_notice` VALUES ('12', '关于系统V1.3更新公告', '2', '2020-03-23 09:12:20', '管理员');
INSERT INTO `sys_notice` VALUES ('14', '关于系统V1.1更新公告', '21321321321<img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\">', '2020-03-23 09:12:20', '超级管理员');
INSERT INTO `sys_notice` VALUES ('15', '关于系统V1.3更新公告', '2', '2020-03-23 09:12:20', '管理员');
INSERT INTO `sys_notice` VALUES ('17', '关于系统V1.1更新公告', '21321321321<img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\">', '2020-03-23 09:12:20', '超级管理员');
INSERT INTO `sys_notice` VALUES ('18', '关于系统V1.3更新公告', '2', '2020-03-23 09:12:20', '管理员');
INSERT INTO `sys_notice` VALUES ('20', '关于系统V1.1更新公告', '21321321321<img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\">', '2020-03-23 09:12:20', '超级管理员');
INSERT INTO `sys_notice` VALUES ('23', '关于系统V1.1更新公告', '21321321321<img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\">', '2020-03-23 09:12:20', '超级管理员');
INSERT INTO `sys_notice` VALUES ('29', '关于系统V1.1更新公告', '21321321321<img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\">', '2020-03-23 09:12:20', '超级管理员');
INSERT INTO `sys_notice` VALUES ('30', '关于系统V1.3更新公告', '2', '2020-03-23 09:12:20', '管理员');
INSERT INTO `sys_notice` VALUES ('32', '关于系统V1.1更新公告', '21321321321<img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\">', '2020-03-23 09:12:20', '超级管理员');
INSERT INTO `sys_notice` VALUES ('33', '关于系统V1.3更新公告', '2', '2020-03-23 09:12:20', '管理员');
INSERT INTO `sys_notice` VALUES ('35', '关于系统V1.1更新公告', '21321321321<img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\">', '2020-03-23 09:12:20', '超级管理员');
INSERT INTO `sys_notice` VALUES ('36', '关于系统V1.3更新公告', '2', '2020-03-23 09:12:20', '管理员');
INSERT INTO `sys_notice` VALUES ('38', '关于系统V1.1更新公告', '21321321321<img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\">', '2020-03-23 09:12:20', '超级管理员');
INSERT INTO `sys_notice` VALUES ('41', '255', '255', '2020-03-22 06:58:21', '超级管理员');
INSERT INTO `sys_notice` VALUES ('42', '关于系统V1.3更新公告', '<p>21321321321&lt;img src=\"/resources/layui/images/face/18.gif\" alt=\"[右哼哼]\"&gt;</p>', '2020-03-22 06:59:28', '超级管理员');
INSERT INTO `sys_notice` VALUES ('44', '111111111111', '111111', '2020-03-22 07:48:31', '超级管理员');
INSERT INTO `sys_notice` VALUES ('46', '很好的', '<b>是防辐射服对方水电费水电费水电费是</b>', '2020-03-22 08:46:47', '超级管理员');
INSERT INTO `sys_notice` VALUES ('47', '222', '<b>2</b>', '2020-03-23 09:12:20', '超级管理员');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT '权限类型[menu/permission]',
  `title` varchar(255) DEFAULT NULL,
  `percode` varchar(255) DEFAULT NULL COMMENT '权限编码[只有type= permission才有  user:view]',
  `icon` varchar(255) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `open` int(11) DEFAULT NULL,
  `ordernum` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT NULL COMMENT '状态【0不可用1可用】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '0', 'menu', '进销存管理系统', null, '&#xe68e;', '', '', '1', '1', '1');
INSERT INTO `sys_permission` VALUES ('2', '1', 'menu', '基础管理', null, '&#xe857;', '', '', '1', '2', '1');
INSERT INTO `sys_permission` VALUES ('3', '1', 'menu', '进货管理', null, '&#xe645;', '', null, '0', '3', '1');
INSERT INTO `sys_permission` VALUES ('4', '1', 'menu', '销售管理', null, '&#xe611;', '', '', '0', '4', '1');
INSERT INTO `sys_permission` VALUES ('5', '1', 'menu', '系统管理', null, '&#xe614;', '', '', '0', '5', '1');
INSERT INTO `sys_permission` VALUES ('6', '1', 'menu', '其它管理', null, '&#xe628;', '', '', '0', '6', '1');
INSERT INTO `sys_permission` VALUES ('7', '2', 'menu', '客户管理', null, '&#xe651;', '/bus/toCustomerManager', '', '0', '7', '1');
INSERT INTO `sys_permission` VALUES ('8', '2', 'menu', '供应商管理', null, '&#xe658;', '/bus/toProviderManager', '', '0', '8', '1');
INSERT INTO `sys_permission` VALUES ('9', '2', 'menu', '商品管理', null, '&#xe657;', '/bus/toGoodsManager', '', '0', '9', '1');
INSERT INTO `sys_permission` VALUES ('10', '3', 'menu', '商品进货', null, '&#xe756;', '/bus/toInportManager', '', '0', '10', '1');
INSERT INTO `sys_permission` VALUES ('11', '3', 'menu', '商品退货查询', null, '&#xe65a;', '/bus/toOutportManager', '', '0', '11', '1');
INSERT INTO `sys_permission` VALUES ('12', '4', 'menu', '商品销售', null, '&#xe65b;', '/bus/toSalesManager', '', '0', '12', '1');
INSERT INTO `sys_permission` VALUES ('13', '4', 'menu', '销售退货查询', null, '&#xe770;', '/bus/toSalesBackManager', '', '0', '13', '1');
INSERT INTO `sys_permission` VALUES ('14', '5', 'menu', '部门管理', null, '&#xe770;', '/sys/toDeptManager', '', '0', '14', '1');
INSERT INTO `sys_permission` VALUES ('15', '5', 'menu', '菜单管理', null, '&#xe857;', '/sys/toMenuManager', '', '0', '15', '1');
INSERT INTO `sys_permission` VALUES ('16', '5', 'menu', '权限管理', '', '&#xe857;', '/sys/toPermissionManager', '', '0', '16', '1');
INSERT INTO `sys_permission` VALUES ('17', '5', 'menu', '角色管理', '', '&#xe650;', '/sys/toRoleManager', '', '0', '17', '1');
INSERT INTO `sys_permission` VALUES ('18', '5', 'menu', '用户管理', '', '&#xe612;', '/sys/toUserManager', '', '0', '18', '1');
INSERT INTO `sys_permission` VALUES ('21', '6', 'menu', '登陆日志', null, '&#xe675;', '/sys/toLogInfoManager', '', '0', '21', '1');
INSERT INTO `sys_permission` VALUES ('22', '6', 'menu', '系统公告', null, '&#xe756;', '/sys/toNoticeManager', null, '0', '22', '1');
INSERT INTO `sys_permission` VALUES ('23', '6', 'menu', '图标管理', null, '&#xe670;', '/sys/toIconManager', null, '0', '23', '1');
INSERT INTO `sys_permission` VALUES ('30', '14', 'permission', '添加部门', 'dept:create', '', null, null, '0', '24', '1');
INSERT INTO `sys_permission` VALUES ('31', '14', 'permission', '修改部门', 'dept:update', '', null, null, '0', '26', '1');
INSERT INTO `sys_permission` VALUES ('32', '14', 'permission', '删除部门', 'dept:delete', '', null, null, '0', '27', '1');
INSERT INTO `sys_permission` VALUES ('34', '15', 'permission', '添加菜单', 'menu:create', '', '', '', '0', '29', '1');
INSERT INTO `sys_permission` VALUES ('35', '15', 'permission', '修改菜单', 'menu:update', '', null, null, '0', '30', '1');
INSERT INTO `sys_permission` VALUES ('36', '15', 'permission', '删除菜单', 'menu:delete', '', null, null, '0', '31', '1');
INSERT INTO `sys_permission` VALUES ('38', '16', 'permission', '添加权限', 'permission:create', '', null, null, '0', '33', '1');
INSERT INTO `sys_permission` VALUES ('39', '16', 'permission', '修改权限', 'permission:update', '', null, null, '0', '34', '1');
INSERT INTO `sys_permission` VALUES ('40', '16', 'permission', '删除权限', 'permission:delete', '', null, null, '0', '35', '1');
INSERT INTO `sys_permission` VALUES ('42', '17', 'permission', '添加角色', 'role:create', '', null, null, '0', '37', '1');
INSERT INTO `sys_permission` VALUES ('43', '17', 'permission', '修改角色', 'role:update', '', null, null, '0', '38', '1');
INSERT INTO `sys_permission` VALUES ('44', '17', 'permission', '角色删除', 'role:delete', '', null, null, '0', '39', '1');
INSERT INTO `sys_permission` VALUES ('46', '17', 'permission', '分配权限', 'role:selectPermission', '', null, null, '0', '41', '1');
INSERT INTO `sys_permission` VALUES ('47', '18', 'permission', '添加用户', 'user:create', '', null, null, '0', '42', '1');
INSERT INTO `sys_permission` VALUES ('48', '18', 'permission', '修改用户', 'user:update', '', null, null, '0', '43', '1');
INSERT INTO `sys_permission` VALUES ('49', '18', 'permission', '删除用户', 'user:delete', '', null, null, '0', '44', '1');
INSERT INTO `sys_permission` VALUES ('51', '18', 'permission', '用户分配角色', 'user:selectRole', '', null, null, '0', '46', '1');
INSERT INTO `sys_permission` VALUES ('52', '18', 'permission', '重置密码', 'user:resetPwd', null, null, null, '0', '47', '1');
INSERT INTO `sys_permission` VALUES ('53', '14', 'permission', '部门查询', 'dept:view', null, null, null, '0', '48', '1');
INSERT INTO `sys_permission` VALUES ('54', '15', 'permission', '菜单查询', 'menu:view', null, null, null, '0', '49', '1');
INSERT INTO `sys_permission` VALUES ('55', '16', 'permission', '权限查询', 'permission:view', null, null, null, '0', '50', '1');
INSERT INTO `sys_permission` VALUES ('56', '17', 'permission', '角色查询', 'role:view', null, null, null, '0', '51', '1');
INSERT INTO `sys_permission` VALUES ('57', '18', 'permission', '用户查询', 'user:view', null, null, null, '0', '52', '1');
INSERT INTO `sys_permission` VALUES ('68', '7', 'permission', '客户查询', 'customer:view', null, null, null, null, '60', '1');
INSERT INTO `sys_permission` VALUES ('69', '7', 'permission', '客户添加', 'customer:create', null, null, null, null, '61', '1');
INSERT INTO `sys_permission` VALUES ('70', '7', 'permission', '客户修改', 'customer:update', null, null, null, null, '62', '1');
INSERT INTO `sys_permission` VALUES ('71', '7', 'permission', '客户删除', 'customer:delete', null, null, null, null, '63', '1');
INSERT INTO `sys_permission` VALUES ('73', '21', 'permission', '日志查询', 'info:view', null, null, null, null, '65', '1');
INSERT INTO `sys_permission` VALUES ('74', '21', 'permission', '日志删除', 'info:delete', null, null, null, null, '66', '1');
INSERT INTO `sys_permission` VALUES ('75', '21', 'permission', '日志批量删除', 'info:batchdelete', null, null, null, null, '67', '1');
INSERT INTO `sys_permission` VALUES ('76', '22', 'permission', '公告查询', 'notice:view', null, null, null, null, '68', '1');
INSERT INTO `sys_permission` VALUES ('77', '22', 'permission', '公告添加', 'notice:create', null, null, null, null, '69', '1');
INSERT INTO `sys_permission` VALUES ('78', '22', 'permission', '公告修改', 'notice:update', null, null, null, null, '70', '1');
INSERT INTO `sys_permission` VALUES ('79', '22', 'permission', '公告删除', 'notice:delete', null, null, null, null, '71', '1');
INSERT INTO `sys_permission` VALUES ('81', '8', 'permission', '供应商查询', 'provider:view', null, null, null, null, '73', '1');
INSERT INTO `sys_permission` VALUES ('82', '8', 'permission', '供应商添加', 'provider:create', null, null, null, null, '74', '1');
INSERT INTO `sys_permission` VALUES ('83', '8', 'permission', '供应商修改', 'provider:update', null, null, null, null, '75', '1');
INSERT INTO `sys_permission` VALUES ('84', '8', 'permission', '供应商删除', 'provider:delete', null, null, null, null, '76', '1');
INSERT INTO `sys_permission` VALUES ('86', '22', 'permission', '公告查看', 'notice:viewnotice', null, null, null, null, '78', '1');
INSERT INTO `sys_permission` VALUES ('91', '9', 'permission', '商品查询', 'goods:view', null, null, null, '0', '79', '1');
INSERT INTO `sys_permission` VALUES ('92', '9', 'permission', '商品添加', 'goods:create', null, null, null, '0', '80', '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('4', '基础数据管理员', '基础数据管理员', '1', '2020-03-23 09:12:20');
INSERT INTO `sys_role` VALUES ('5', '仓库管理员', '仓库管理员', '1', '2020-03-23 09:12:20');
INSERT INTO `sys_role` VALUES ('6', '销售管理员', '销售管理员', '1', '2020-03-23 09:12:20');
INSERT INTO `sys_role` VALUES ('7', '系统管理员', '系统管理员', '1', '2020-03-23 09:12:20');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`pid`,`rid`) USING BTREE,
  KEY `FK_tcsr9ucxypb3ce1q5qngsfk33` (`rid`) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('4', '1');
INSERT INTO `sys_role_permission` VALUES ('4', '2');
INSERT INTO `sys_role_permission` VALUES ('4', '7');
INSERT INTO `sys_role_permission` VALUES ('4', '8');
INSERT INTO `sys_role_permission` VALUES ('4', '9');
INSERT INTO `sys_role_permission` VALUES ('4', '68');
INSERT INTO `sys_role_permission` VALUES ('4', '69');
INSERT INTO `sys_role_permission` VALUES ('4', '70');
INSERT INTO `sys_role_permission` VALUES ('4', '71');
INSERT INTO `sys_role_permission` VALUES ('4', '81');
INSERT INTO `sys_role_permission` VALUES ('4', '82');
INSERT INTO `sys_role_permission` VALUES ('4', '83');
INSERT INTO `sys_role_permission` VALUES ('4', '84');
INSERT INTO `sys_role_permission` VALUES ('4', '91');
INSERT INTO `sys_role_permission` VALUES ('4', '92');
INSERT INTO `sys_role_permission` VALUES ('5', '1');
INSERT INTO `sys_role_permission` VALUES ('5', '3');
INSERT INTO `sys_role_permission` VALUES ('5', '10');
INSERT INTO `sys_role_permission` VALUES ('5', '11');
INSERT INTO `sys_role_permission` VALUES ('6', '1');
INSERT INTO `sys_role_permission` VALUES ('6', '4');
INSERT INTO `sys_role_permission` VALUES ('6', '12');
INSERT INTO `sys_role_permission` VALUES ('6', '13');
INSERT INTO `sys_role_permission` VALUES ('7', '1');
INSERT INTO `sys_role_permission` VALUES ('7', '5');
INSERT INTO `sys_role_permission` VALUES ('7', '6');
INSERT INTO `sys_role_permission` VALUES ('7', '14');
INSERT INTO `sys_role_permission` VALUES ('7', '15');
INSERT INTO `sys_role_permission` VALUES ('7', '16');
INSERT INTO `sys_role_permission` VALUES ('7', '17');
INSERT INTO `sys_role_permission` VALUES ('7', '18');
INSERT INTO `sys_role_permission` VALUES ('7', '21');
INSERT INTO `sys_role_permission` VALUES ('7', '22');
INSERT INTO `sys_role_permission` VALUES ('7', '23');
INSERT INTO `sys_role_permission` VALUES ('7', '30');
INSERT INTO `sys_role_permission` VALUES ('7', '31');
INSERT INTO `sys_role_permission` VALUES ('7', '32');
INSERT INTO `sys_role_permission` VALUES ('7', '34');
INSERT INTO `sys_role_permission` VALUES ('7', '35');
INSERT INTO `sys_role_permission` VALUES ('7', '36');
INSERT INTO `sys_role_permission` VALUES ('7', '38');
INSERT INTO `sys_role_permission` VALUES ('7', '39');
INSERT INTO `sys_role_permission` VALUES ('7', '40');
INSERT INTO `sys_role_permission` VALUES ('7', '42');
INSERT INTO `sys_role_permission` VALUES ('7', '43');
INSERT INTO `sys_role_permission` VALUES ('7', '44');
INSERT INTO `sys_role_permission` VALUES ('7', '46');
INSERT INTO `sys_role_permission` VALUES ('7', '47');
INSERT INTO `sys_role_permission` VALUES ('7', '48');
INSERT INTO `sys_role_permission` VALUES ('7', '49');
INSERT INTO `sys_role_permission` VALUES ('7', '51');
INSERT INTO `sys_role_permission` VALUES ('7', '52');
INSERT INTO `sys_role_permission` VALUES ('7', '53');
INSERT INTO `sys_role_permission` VALUES ('7', '54');
INSERT INTO `sys_role_permission` VALUES ('7', '55');
INSERT INTO `sys_role_permission` VALUES ('7', '56');
INSERT INTO `sys_role_permission` VALUES ('7', '57');
INSERT INTO `sys_role_permission` VALUES ('7', '73');
INSERT INTO `sys_role_permission` VALUES ('7', '74');
INSERT INTO `sys_role_permission` VALUES ('7', '75');
INSERT INTO `sys_role_permission` VALUES ('7', '76');
INSERT INTO `sys_role_permission` VALUES ('7', '77');
INSERT INTO `sys_role_permission` VALUES ('7', '78');
INSERT INTO `sys_role_permission` VALUES ('7', '79');
INSERT INTO `sys_role_permission` VALUES ('7', '86');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `rid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  PRIMARY KEY (`uid`,`rid`) USING BTREE,
  KEY `FK_203gdpkwgtow7nxlo2oap5jru` (`rid`) USING BTREE,
  CONSTRAINT `sys_role_user_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `sys_role_user_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('4', '2');
INSERT INTO `sys_role_user` VALUES ('4', '3');
INSERT INTO `sys_role_user` VALUES ('4', '4');
INSERT INTO `sys_role_user` VALUES ('4', '5');
INSERT INTO `sys_role_user` VALUES ('5', '2');
INSERT INTO `sys_role_user` VALUES ('5', '3');
INSERT INTO `sys_role_user` VALUES ('5', '4');
INSERT INTO `sys_role_user` VALUES ('5', '5');
INSERT INTO `sys_role_user` VALUES ('5', '6');
INSERT INTO `sys_role_user` VALUES ('5', '7');
INSERT INTO `sys_role_user` VALUES ('6', '2');
INSERT INTO `sys_role_user` VALUES ('6', '3');
INSERT INTO `sys_role_user` VALUES ('6', '4');
INSERT INTO `sys_role_user` VALUES ('6', '5');
INSERT INTO `sys_role_user` VALUES ('6', '6');
INSERT INTO `sys_role_user` VALUES ('7', '2');
INSERT INTO `sys_role_user` VALUES ('7', '4');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `loginname` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `deptid` int(11) DEFAULT NULL,
  `hiredate` datetime DEFAULT NULL,
  `mgr` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT '1',
  `ordernum` int(11) DEFAULT NULL,
  `type` int(255) DEFAULT NULL COMMENT '用户类型[0超级管理员1，管理员，2普通用户]',
  `imgpath` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_3rrcpvho2w1mx1sfiuuyir1h` (`deptid`) USING BTREE,
  CONSTRAINT `sys_user_ibfk_1` FOREIGN KEY (`deptid`) REFERENCES `sys_dept` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '超级管理员', 'system', '系统深处的男人', '1', '超级管理员', '532ac00e86893901af5f0be6b704dbc7', '1', '2020-03-23 09:12:20', null, '1', '1', '0', '../resources/images/defaultusertitle.jpg', '04A93C74C8294AA09A8B974FD1F4ECBB');
INSERT INTO `sys_user` VALUES ('2', '李四', 'ls', '武汉', '0', 'KING', 'b07b848d69e0553b80e601d31571797e', '1', '2020-03-23 09:12:20', null, '1', '2', '1', '../resources/images/defaultusertitle.jpg', 'FC1EE06AE4354D3FBF7FDD15C8FCDA71');
INSERT INTO `sys_user` VALUES ('3', '王五', 'ww', '武汉', '1', '管理员', '3c3f971eae61e097f59d52360323f1c8', '3', '2020-03-23 09:12:20', '2', '1', '3', '1', '../resources/images/defaultusertitle.jpg', '3D5F956E053C4E85B7D2681386E235D2');
INSERT INTO `sys_user` VALUES ('4', '赵六', 'zl', '武汉', '1', '程序员', '2e969742a7ea0c7376e9551d578e05dd', '4', '2020-03-23 09:12:20', '3', '1', '4', '1', '../resources/images/defaultusertitle.jpg', '6480EE1391E34B0886ACADA501E31145');
INSERT INTO `sys_user` VALUES ('5', '孙七', 'sq', '武汉', '1', '程序员', '47b4c1ad6e4b54dd9387a09cb5a03de1', '2', '2020-03-23 09:12:20', '4', '1', '5', '1', '../resources/images/defaultusertitle.jpg', 'FE3476C3E3674E5690C737C269FCBF8E');
INSERT INTO `sys_user` VALUES ('6', '刘八', 'lb', '深圳', '1', '程序员', 'bcee2b05b4b591106829aec69a094806', '4', '2020-03-23 09:12:20', '3', '1', '6', '1', '../resources/images/defaultusertitle.jpg', 'E6CCF54A09894D998225878BBD139B20');
INSERT INTO `sys_user` VALUES ('7', '小剑', 'xiaojian', '北京', '1', '你好', '67cbd99643f5222af151f60c35d4d2a2', '4', '2020-03-23 09:12:20', '4', '1', '7', '1', null, '38BBB5EF1B61487BABB8745280BD0E1D');

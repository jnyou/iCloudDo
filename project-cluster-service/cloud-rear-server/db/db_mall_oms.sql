/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : gmall_oms

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 23/02/2021 14:19:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `order_sn` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '使用的优惠券',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  `member_username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '订单总额',
  `pay_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '应付总额',
  `freight_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '运费金额',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '促销优化金额（促销价、满减、阶梯价）',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '积分抵扣金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠券抵扣金额',
  `discount_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '后台调整订单使用的折扣金额',
  `pay_type` tinyint(4) NULL DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `source_type` tinyint(4) NULL DEFAULT NULL COMMENT '订单来源[0->PC订单；1->app订单]',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `delivery_company` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `auto_confirm_day` int(11) NULL DEFAULT NULL COMMENT '自动确认时间（天）',
  `integration` int(11) NULL DEFAULT NULL COMMENT '可以获得的积分',
  `growth` int(11) NULL DEFAULT NULL COMMENT '可以获得的成长值',
  `bill_type` tinyint(4) NULL DEFAULT NULL COMMENT '发票类型[0->不开发票；1->电子发票；2->纸质发票]',
  `bill_header` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收票人电话',
  `bill_receiver_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
  `receiver_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `receiver_detail_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `confirm_status` tinyint(4) NULL DEFAULT NULL COMMENT '确认收货状态[0->未确认；1->已确认]',
  `delete_status` tinyint(4) NULL DEFAULT NULL COMMENT '删除状态【0->未删除；1->已删除】',
  `use_integration` int(11) NULL DEFAULT NULL COMMENT '下单时使用的积分',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime(0) NULL DEFAULT NULL COMMENT '评价时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_sn`(`order_sn`) USING BTREE COMMENT '订单id为主键，防止出现不幂等情况'
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (14, 3, '202102081617027131358691295270285314', NULL, '2021-02-08 08:17:03', NULL, 12598.0000, 12607.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 12598, 12598, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-08 08:17:03');
INSERT INTO `oms_order` VALUES (15, 3, '202102081642282361358697693790101506', NULL, '2021-02-08 08:42:28', NULL, 12598.0000, 12607.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 12598, 12598, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-08 08:42:28');
INSERT INTO `oms_order` VALUES (16, 3, '202102081741404401358712592813539330', NULL, '2021-02-08 09:41:41', NULL, 12598.0000, 12607.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 12598, 12598, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-08 09:41:41');
INSERT INTO `oms_order` VALUES (19, 3, '202102131853396031360542648028368898', NULL, '2021-02-13 10:53:40', NULL, 19397.0000, 19406.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 19397, 19397, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-13 10:53:40');
INSERT INTO `oms_order` VALUES (20, 3, '202102131855002601360542986311569409', NULL, '2021-02-13 10:55:00', NULL, 19397.0000, 19406.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 19397, 19397, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-13 10:55:00');
INSERT INTO `oms_order` VALUES (21, 3, '202102142042210001360932388611407873', NULL, '2021-02-14 12:42:21', NULL, 19397.0000, 19406.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 19397, 19397, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-14 12:42:21');
INSERT INTO `oms_order` VALUES (22, 3, '202102151706537061361240555475976194', NULL, '2021-02-15 09:06:54', NULL, 19397.0000, 19402.0000, 5.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 19397, 19397, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456785', '331100', '江西省', '南昌市', '新建区', '金峰天下', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-15 09:06:54');
INSERT INTO `oms_order` VALUES (23, 3, '202102151749067591361251179870412802', NULL, '2021-02-15 09:49:07', NULL, 6299.0000, 6308.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 6299, 6299, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-15 09:49:07');
INSERT INTO `oms_order` VALUES (24, 3, '202102151750145371361251464139366402', NULL, '2021-02-15 09:50:15', NULL, 6299.0000, 6308.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 6299, 6299, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-15 09:50:15');
INSERT INTO `oms_order` VALUES (25, 3, '202102151753025441361252168824262658', NULL, '2021-02-15 09:53:03', NULL, 6299.0000, 6308.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 6299, 6299, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-15 09:53:03');
INSERT INTO `oms_order` VALUES (26, 3, '202102151756204031361252998692474882', NULL, '2021-02-15 09:56:20', NULL, 6299.0000, 6308.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 6299, 6299, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-15 09:56:20');
INSERT INTO `oms_order` VALUES (27, 3, '202102151756520931361253131609968641', NULL, '2021-02-15 09:56:52', NULL, 6299.0000, 6308.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 6299, 6299, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-15 09:56:52');
INSERT INTO `oms_order` VALUES (28, 3, '202102151757399571361253332366135298', NULL, '2021-02-15 09:57:40', NULL, 6299.0000, 6304.0000, 5.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 6299, 6299, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456785', '331100', '江西省', '南昌市', '新建区', '金峰天下', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-15 09:57:40');
INSERT INTO `oms_order` VALUES (29, 3, '202102151804522491361255145538600962', NULL, '2021-02-15 10:04:52', NULL, 13098.0000, 13103.0000, 5.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 13098, 13098, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456785', '331100', '江西省', '南昌市', '新建区', '金峰天下', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-15 10:04:52');
INSERT INTO `oms_order` VALUES (30, 3, '202102151806455191361255620631703554', NULL, '2021-02-15 10:06:46', NULL, 13098.0000, 13107.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, 1, NULL, 1, NULL, NULL, 7, 13098, 13098, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-15 10:06:46');
INSERT INTO `oms_order` VALUES (31, 3, '202102191848050661362715572067917825', NULL, NULL, NULL, NULL, 6399.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (32, 3, '202102191858231541362718164500111361', NULL, NULL, NULL, NULL, 6399.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (33, 3, '202102191900313951362718702394466305', NULL, NULL, NULL, NULL, 6399.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (34, 3, '202102191903586621362719571726884865', NULL, NULL, NULL, NULL, 5999.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (36, 3, '202102212017073851363462755092271105', NULL, '2021-02-21 12:17:07', NULL, 6799.0000, 6808.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 6799, 6799, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-21 12:17:07');
INSERT INTO `oms_order` VALUES (37, 3, '202102212026138971363465047342346242', NULL, NULL, NULL, NULL, 6399.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (38, 3, '202102221425006901363736531193421825', NULL, NULL, NULL, NULL, 6199.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (40, 3, '202102221430023221363737796312838145', NULL, '2021-02-22 06:30:02', NULL, 6799.0000, 6808.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 0, NULL, NULL, 7, 6799, 6799, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-22 06:30:02');
INSERT INTO `oms_order` VALUES (41, 3, '202102221443345701363741203127873538', NULL, '2021-02-22 06:43:35', NULL, 6799.0000, 6808.0000, 9.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 6799, 6799, NULL, NULL, NULL, NULL, NULL, '傻瓜太在意小可爱', '123456789', '331100', '北京市', '北京市', '昌平区', '天通苑', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, '2021-02-22 06:43:35');

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spu_name',
  `spu_pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spu_pic',
  `spu_brand` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '商品分类id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品sku名字',
  `sku_pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品sku图片',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品sku价格',
  `sku_quantity` int(11) NULL DEFAULT NULL COMMENT '商品购买的数量',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品销售属性组合（JSON）',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品促销分解金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠券优惠分解金额',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '积分优惠分解金额',
  `real_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
  `gift_integration` int(11) NULL DEFAULT NULL COMMENT '赠送积分',
  `gift_growth` int(11) NULL DEFAULT NULL COMMENT '赠送成长值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单项信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
INSERT INTO `oms_order_item` VALUES (9, NULL, '202102051342200841357565197501349890', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 3, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 18897.0000, 18897, 18897);
INSERT INTO `oms_order_item` VALUES (10, NULL, '202102081617027131358691295270285314', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 2, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 12598.0000, 12598, 12598);
INSERT INTO `oms_order_item` VALUES (11, NULL, '202102081642282361358697693790101506', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 2, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 12598.0000, 12598, 12598);
INSERT INTO `oms_order_item` VALUES (12, NULL, '202102081741404401358712592813539330', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 2, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 12598.0000, 12598, 12598);
INSERT INTO `oms_order_item` VALUES (17, NULL, '202102131853396031360542648028368898', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 2, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 12598.0000, 12598, 12598);
INSERT INTO `oms_order_item` VALUES (18, NULL, '202102131853396031360542648028368898', 10, 'Apple iPhone 12', NULL, NULL, 225, 11, 'Apple iPhone 12 红色全网通5G手机双卡双待 128GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/d1624201-aed6-447d-8e22-f309750ccc45_5b5e74d0978360a1.jpg', 6799.0000, 1, '颜色：红色;版本：128GB', 0.0000, 0.0000, 0.0000, 6799.0000, 6799, 6799);
INSERT INTO `oms_order_item` VALUES (19, NULL, '202102131855002601360542986311569409', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 2, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 12598.0000, 12598, 12598);
INSERT INTO `oms_order_item` VALUES (20, NULL, '202102131855002601360542986311569409', 10, 'Apple iPhone 12', NULL, NULL, 225, 11, 'Apple iPhone 12 红色全网通5G手机双卡双待 128GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/d1624201-aed6-447d-8e22-f309750ccc45_5b5e74d0978360a1.jpg', 6799.0000, 1, '颜色：红色;版本：128GB', 0.0000, 0.0000, 0.0000, 6799.0000, 6799, 6799);
INSERT INTO `oms_order_item` VALUES (21, NULL, '202102142042210001360932388611407873', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 2, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 12598.0000, 12598, 12598);
INSERT INTO `oms_order_item` VALUES (22, NULL, '202102142042210001360932388611407873', 10, 'Apple iPhone 12', NULL, NULL, 225, 11, 'Apple iPhone 12 红色全网通5G手机双卡双待 128GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/d1624201-aed6-447d-8e22-f309750ccc45_5b5e74d0978360a1.jpg', 6799.0000, 1, '颜色：红色;版本：128GB', 0.0000, 0.0000, 0.0000, 6799.0000, 6799, 6799);
INSERT INTO `oms_order_item` VALUES (23, NULL, '202102151706537061361240555475976194', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 2, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 12598.0000, 12598, 12598);
INSERT INTO `oms_order_item` VALUES (24, NULL, '202102151706537061361240555475976194', 10, 'Apple iPhone 12', NULL, NULL, 225, 11, 'Apple iPhone 12 红色全网通5G手机双卡双待 128GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/d1624201-aed6-447d-8e22-f309750ccc45_5b5e74d0978360a1.jpg', 6799.0000, 1, '颜色：红色;版本：128GB', 0.0000, 0.0000, 0.0000, 6799.0000, 6799, 6799);
INSERT INTO `oms_order_item` VALUES (25, NULL, '202102151749067591361251179870412802', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 1, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 6299.0000, 6299, 6299);
INSERT INTO `oms_order_item` VALUES (26, NULL, '202102151750145371361251464139366402', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 1, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 6299.0000, 6299, 6299);
INSERT INTO `oms_order_item` VALUES (27, NULL, '202102151753025441361252168824262658', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 1, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 6299.0000, 6299, 6299);
INSERT INTO `oms_order_item` VALUES (28, NULL, '202102151756204031361252998692474882', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 1, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 6299.0000, 6299, 6299);
INSERT INTO `oms_order_item` VALUES (29, NULL, '202102151756520931361253131609968641', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 1, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 6299.0000, 6299, 6299);
INSERT INTO `oms_order_item` VALUES (30, NULL, '202102151757399571361253332366135298', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 1, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 6299.0000, 6299, 6299);
INSERT INTO `oms_order_item` VALUES (31, NULL, '202102151804522491361255145538600962', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 1, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 6299.0000, 6299, 6299);
INSERT INTO `oms_order_item` VALUES (32, NULL, '202102151804522491361255145538600962', 10, 'Apple iPhone 12', NULL, NULL, 225, 11, 'Apple iPhone 12 红色全网通5G手机双卡双待 128GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/d1624201-aed6-447d-8e22-f309750ccc45_5b5e74d0978360a1.jpg', 6799.0000, 1, '颜色：红色;版本：128GB', 0.0000, 0.0000, 0.0000, 6799.0000, 6799, 6799);
INSERT INTO `oms_order_item` VALUES (33, NULL, '202102151806455191361255620631703554', 10, 'Apple iPhone 12', NULL, NULL, 225, 7, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/11d7b873-9d24-43dc-81a7-ddbdecae113f_a2c208410ae84d1f.jpg', 6299.0000, 1, '颜色：黑色;版本：64GB', 0.0000, 0.0000, 0.0000, 6299.0000, 6299, 6299);
INSERT INTO `oms_order_item` VALUES (34, NULL, '202102151806455191361255620631703554', 10, 'Apple iPhone 12', NULL, NULL, 225, 11, 'Apple iPhone 12 红色全网通5G手机双卡双待 128GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/d1624201-aed6-447d-8e22-f309750ccc45_5b5e74d0978360a1.jpg', 6799.0000, 1, '颜色：红色;版本：128GB', 0.0000, 0.0000, 0.0000, 6799.0000, 6799, 6799);
INSERT INTO `oms_order_item` VALUES (35, NULL, '202102191848050661362715572067917825', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6399.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (36, NULL, '202102191858231541362718164500111361', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6399.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (37, NULL, '202102191900313951362718702394466305', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6399.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (38, NULL, '202102191903586621362719571726884865', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 5999.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (41, NULL, '202102212017073851363462755092271105', 10, 'Apple iPhone 12', NULL, NULL, 225, 11, 'Apple iPhone 12 红色全网通5G手机双卡双待 128GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/d1624201-aed6-447d-8e22-f309750ccc45_5b5e74d0978360a1.jpg', 6799.0000, 1, '颜色：红色;版本：128GB', 0.0000, 0.0000, 0.0000, 6799.0000, 6799, 6799);
INSERT INTO `oms_order_item` VALUES (42, NULL, '202102212026138971363465047342346242', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6399.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (43, NULL, '202102221425006901363736531193421825', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 6199.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (45, NULL, '202102221430023221363737796312838145', 10, 'Apple iPhone 12', NULL, NULL, 225, 11, 'Apple iPhone 12 红色全网通5G手机双卡双待 128GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/d1624201-aed6-447d-8e22-f309750ccc45_5b5e74d0978360a1.jpg', 6799.0000, 1, '颜色：红色;版本：128GB', 0.0000, 0.0000, 0.0000, 6799.0000, 6799, 6799);
INSERT INTO `oms_order_item` VALUES (46, NULL, '202102221443345701363741203127873538', 10, 'Apple iPhone 12', NULL, NULL, 225, 11, 'Apple iPhone 12 红色全网通5G手机双卡双待 128GB', 'https://smallsword-mall.oss-cn-beijing.aliyuncs.com/2020-11-29/d1624201-aed6-447d-8e22-f309750ccc45_5b5e74d0978360a1.jpg', 6799.0000, 1, '颜色：红色;版本：128GB', 0.0000, 0.0000, 0.0000, 6799.0000, 6799, 6799);

-- ----------------------------
-- Table structure for oms_order_operate_history
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_operate_history`;
CREATE TABLE `oms_order_operate_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `operate_man` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `order_status` tinyint(4) NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单操作历史记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_operate_history
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_return_apply
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_apply`;
CREATE TABLE `oms_order_return_apply`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '退货商品id',
  `order_sn` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `member_username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员用户名',
  `return_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `return_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人姓名',
  `return_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人电话',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
  `sku_img` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `sku_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `sku_brand` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品品牌',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品销售属性(JSON)',
  `sku_count` int(11) NULL DEFAULT NULL COMMENT '退货数量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品单价',
  `sku_real_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品实际支付单价',
  `reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原因',
  `description述` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `desc_pics` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '凭证图片，以逗号隔开',
  `handle_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理备注',
  `handle_man` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人员',
  `receive_man` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '收货时间',
  `receive_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货备注',
  `receive_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货电话',
  `company_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司收货地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单退货申请' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_return_apply
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_return_reason
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_reason`;
CREATE TABLE `oms_order_return_reason`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货原因名',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '退货原因' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_return_reason
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int(11) NULL DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int(11) NULL DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int(11) NULL DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int(11) NULL DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
  `comment_overtime` int(11) NULL DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `member_level` tinyint(2) NULL DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单配置信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_setting
-- ----------------------------

-- ----------------------------
-- Table structure for oms_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_payment_info`;
CREATE TABLE `oms_payment_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号（对外业务号）',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `alipay_trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付宝交易流水号',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '支付总金额',
  `subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易内容',
  `payment_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `confirm_time` datetime(0) NULL DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回调内容',
  `callback_time` datetime(0) NULL DEFAULT NULL COMMENT '回调时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_sn`(`order_sn`) USING BTREE,
  UNIQUE INDEX `alipay_trade_no`(`alipay_trade_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '支付信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_payment_info
-- ----------------------------
INSERT INTO `oms_payment_info` VALUES (1, '10000', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_payment_info` VALUES (2, '202102151804522491361255145538600962', NULL, '2021021522001483890501454793', NULL, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'TRADE_SUCCESS', '2021-02-15 10:05:16', NULL, NULL, '2021-02-15 10:05:13');
INSERT INTO `oms_payment_info` VALUES (3, '202102151806455191361255620631703554', NULL, '2021021522001483890501454942', NULL, 'Apple iPhone 12 黑色全网通5G手机双卡双待 64GB', 'TRADE_SUCCESS', '2021-02-15 10:07:05', NULL, NULL, '2021-02-15 10:07:03');

-- ----------------------------
-- Table structure for oms_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_refund_info`;
CREATE TABLE `oms_refund_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_return_id` bigint(20) NULL DEFAULT NULL COMMENT '退款的订单',
  `refund` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `refund_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款交易流水号',
  `refund_status` tinyint(1) NULL DEFAULT NULL COMMENT '退款状态',
  `refund_channel` tinyint(4) NULL DEFAULT NULL COMMENT '退款渠道[1-支付宝，2-微信，3-银联，4-汇款]',
  `refund_content` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '退款信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_refund_info
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

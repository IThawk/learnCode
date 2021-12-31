/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : pro

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2020-03-04 21:35:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1513 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('1508', '1', 'e934526b-ef0d-4864-81cb-ef3c6aa42ea4');
INSERT INTO `order` VALUES ('1509', '1', '8c675483-d66b-4100-a902-fca628cead5c');
INSERT INTO `order` VALUES ('1510', '1', '7098055c-a4fd-4c8b-aca7-dec77b88d1b0');
INSERT INTO `order` VALUES ('1511', '1', '3ec6e225-0efa-4640-be36-52e95b4c9b03');
INSERT INTO `order` VALUES ('1512', '1', '1b5b5335-b5c8-4b57-8a58-fbd45670e060');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '元旦大礼包', '0', '0');

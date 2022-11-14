/*
Navicat MySQL Data Transfer

Source Server         : 192.168.56.101
Source Server Version : 50734
Source Host           : 192.168.56.101:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2021-04-23 13:44:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', 'test1', '2');

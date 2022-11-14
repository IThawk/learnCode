/*
Navicat MySQL Data Transfer

Source Server         : docker-mysql
Source Server Version : 50736
Source Host           : 127.0.0.1:3306
Source Database       : redis-test

Target Server Type    : MYSQL
Target Server Version : 50736
File Encoding         : 65001

Date: 2021-12-19 17:49:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `sex` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'lisi', '11111', '1', '1', '2021-12-14 17:58:08', '2021-12-14 17:58:11');

-- ----------------------------
-- Table structure for user_sign
-- ----------------------------
DROP TABLE IF EXISTS `user_sign`;
CREATE TABLE `user_sign` (
  `keyid` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_key` varchar(200) DEFAULT NULL COMMENT '#京东用户ID',
  `sign_date` datetime DEFAULT NULL COMMENT '#签到日期(20210618)',
  `sign_count` int(11) DEFAULT NULL COMMENT '#连续签到天数',
  PRIMARY KEY (`keyid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_sign
-- ----------------------------
INSERT INTO `user_sign` VALUES ('1', '20210618-xxxx-xxxx-xxxx-xxxxxxxxxxxx', '2020-06-18 15:11:12', '1');

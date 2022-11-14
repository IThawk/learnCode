/*
Navicat H2 Data Transfer

*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tab_category`
-- ----------------------------
DROP TABLE IF EXISTS `tab_category`;
CREATE TABLE `tab_category` (
  `category_id` int NOT NULL auto_increment,
  `category_name` varchar(255)  DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ;
comment on table tab_category is '旅游类别';
-- ----------------------------
-- Records of tab_category
-- ----------------------------
INSERT INTO `tab_category` VALUES ('14', '国内游轻化工');
INSERT INTO `tab_category` VALUES ('15', '境外游');

-- ----------------------------
-- Table structure for `tab_route`
-- ----------------------------
DROP TABLE IF EXISTS `tab_route`;
CREATE TABLE `tab_route` (
  `route_id` int NOT NULL auto_increment ,
  `route_name` varchar(255)  DEFAULT NULL,
  `route_price` double DEFAULT NULL,
  `route_introduce` varchar(255) DEFAULT NULL,
  `route_flag` int DEFAULT NULL,
  `route_theme_tour` int DEFAULT NULL,
  `route_count` int DEFAULT NULL,
  `route_category_id` int DEFAULT NULL,
  `route_image` varchar(255) DEFAULT NULL,
  `route_seller_id` int DEFAULT NULL,
  `route_source_id` int DEFAULT NULL,
  PRIMARY KEY (`route_id`)
) ;
comment on table tab_route is '旅游路线';
-- ----------------------------
-- Records of tab_route
-- ----------------------------

-- ----------------------------
-- Table structure for `tab_route_image`
-- ----------------------------
DROP TABLE IF EXISTS `tab_route_image`;
CREATE TABLE `tab_route_image` (
  `ri_id` int NOT NULL auto_increment,
  `ri_route_id` int DEFAULT NULL,
  `ri_pic` varchar(255) DEFAULT NULL,
  `ri_size` int DEFAULT NULL COMMENT '1 大图 0 小图',
  PRIMARY KEY (`ri_id`)
) ;
comment on table tab_route_image is '旅游地图';
-- ----------------------------
-- Records of tab_route_image
-- ----------------------------
INSERT INTO `tab_route_image` VALUES ('85', '94', 'image/product/big/8e2a0515511a43f1af6f5c231cd42092.png', '1');
INSERT INTO `tab_route_image` VALUES ('86', '94', 'image/product/big/9c4fb08da0e34f268cdf5868b04c321b.jpg', '1');
INSERT INTO `tab_route_image` VALUES ('87', '94', 'image/product/big/e990cea1c1624d62bade48e7ec423619.jpg', '1');
INSERT INTO `tab_route_image` VALUES ('88', '94', 'image/product/big/c16bb52783434640890d57698a748fc7.jpg', '1');
INSERT INTO `tab_route_image` VALUES ('89', '94', 'image/product/small/6264ff01f5ef4f599e8c09bdd3a94f10.jpg', '0');
INSERT INTO `tab_route_image` VALUES ('90', '94', 'image/product/small/5d700d58aafa49e590acc310a3c92ad1.jpg', '0');
INSERT INTO `tab_route_image` VALUES ('91', '94', 'image/product/small/3afada008c864329ad5f5a92c7d14455.jpg', '0');
INSERT INTO `tab_route_image` VALUES ('92', '94', 'image/product/small/bf92cec4eedd416da7be21f9a183f2b8.jpg', '0');

-- ----------------------------
-- Table structure for `tab_seller`
-- ----------------------------
DROP TABLE IF EXISTS `tab_seller`;
CREATE TABLE `tab_seller` (
  `seller_id` int NOT NULL auto_increment COMMENT '主键',
  `seller_name` varchar(255)  DEFAULT NULL COMMENT '名称',
  `seller_phone` varchar(255)  DEFAULT NULL COMMENT '联系方式',
  `seller_address` varchar(255)  DEFAULT NULL COMMENT '公司地址',
  PRIMARY KEY (`seller_id`)
) ;
comment on table tab_seller is '旅游公司';
-- ----------------------------
-- Records of tab_seller
-- ----------------------------
INSERT INTO `tab_seller` VALUES ('91', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('92', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('93', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('94', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('95', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('96', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('97', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('98', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('99', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('103', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('104', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('105', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('106', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('107', '1', '123456789', '北京市天门广场扬州分厂');
INSERT INTO `tab_seller` VALUES ('108', '1', '123456789', '北京市天门广场扬州分厂');

-- ----------------------------
-- Table structure for `tab_user`
-- ----------------------------
DROP TABLE IF EXISTS `tab_user`;
CREATE TABLE `tab_user` (
  `user_id` int NOT NULL auto_increment,
  `user_username` varchar(255)  DEFAULT NULL,
  `user_password` varchar(255)  DEFAULT NULL,
  `user_real_name` varchar(255)  DEFAULT NULL,
  `user_birthday` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `user_sex` int DEFAULT NULL,
  `user_phone` varchar(255)  DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `user_status` varchar(255) DEFAULT NULL,
  `user_code` varchar(255) DEFAULT NULL,
  `user_admin` varchar(255)DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ;
comment on table tab_user is '用户表';
-- ----------------------------
-- Records of tab_user
-- ----------------------------
INSERT INTO `tab_user` VALUES ('77', 'test11', '22222', '测试', '2021-06-09 16:00:00', '1', '1198745241', 'test@163.com', '', '', '');
INSERT INTO `tab_user` VALUES ('78', '徐三', '111111', '徐福', '1990-03-02 16:00:00', '1', '12345678910', 'tesp@qq.com', '', '', '');
INSERT INTO `tab_user` VALUES ('80', '徐三', '111111', '徐福', '1990-03-02 16:00:00', '1', '12345678910', 'tesp@qq.com', '', '', '');
INSERT INTO `tab_user` VALUES ('81', 'test', '123456', 'test', '2021-06-09 16:00:00', '0', '1198745241', 'test@163.com', '', '', '');
INSERT INTO `tab_user` VALUES ('82', '徐三', '111111', '徐福', '1990-03-02 16:00:00', '1', '12345678910', 'tesp@qq.com', '', '', '');
INSERT INTO `tab_user` VALUES ('83', 'test', '123456', 'test', '2021-06-09 16:00:00', '0', '1198745241', 'test@163.com', '', '', '');
INSERT INTO `tab_user` VALUES ('84', '徐三', '111111', '徐福', '1990-03-02 16:00:00', '1', '12345678910', 'tesp@qq.com', '', '', '');
INSERT INTO `tab_user` VALUES ('85', 'test', '123456', 'test', '2021-06-09 16:00:00', '0', '1198745241', 'test@163.com', '', '', '');
INSERT INTO `tab_user` VALUES ('86', '徐三', '111111', '徐福', '1990-03-02 16:00:00', '1', '12345678910', 'tesp@qq.com', '', '', '');
INSERT INTO `tab_user` VALUES ('87', 'test', '123456', 'test', '2021-06-09 16:00:00', '0', '1198745241', 'test@163.com', '', '', '');



-- ----------------------------
-- Table structure for `tab_user`
-- ----------------------------
DROP TABLE IF EXISTS `tab_project`;
CREATE TABLE `tab_project` (
                            `project_id` int NOT NULL auto_increment,
                            `project_name` varchar(255)  DEFAULT NULL,
                            PRIMARY KEY (`project_id`)
) ;
comment on table tab_project is '項目表';
-- ----------------------------
-- Records of tab_user
-- ----------------------------
INSERT INTO `tab_project` VALUES ('77', 'test11');

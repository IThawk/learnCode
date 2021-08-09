CREATE TABLE `tbl_dept` (
  `dept_id` int(16) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `tbl_dept`(`dept_id`, `dept_name`) VALUES (1, '开发部');
INSERT INTO `tbl_dept`(`dept_id`, `dept_name`) VALUES (2, '设计部');



CREATE TABLE `tbl_emp` (
  `emp_id` int(16) NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(64) DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `d_id` int(16) DEFAULT NULL,
  PRIMARY KEY (`emp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=167016 DEFAULT CHARSET=utf8;

INSERT INTO `tbl_emp` (`emp_id`, `emp_name`, `gender`, `email`, `d_id`) VALUES (1, '王路', 'M', 'a@b.com', 2);
INSERT INTO `tbl_emp` (`emp_id`, `emp_name`, `gender`, `email`, `d_id`) VALUES (2, '李心', 'F', 'a@b.com', 2);
INSERT INTO `tbl_emp` (`emp_id`, `emp_name`, `gender`, `email`, `d_id`) VALUES (3, '杨杰', 'F', 'a@b.com', 1);
INSERT INTO `tbl_emp` (`emp_id`, `emp_name`, `gender`, `email`, `d_id`) VALUES (4, '候诚', 'F', 'a@b.com', 2);
INSERT INTO `tbl_emp` (`emp_id`, `emp_name`, `gender`, `email`, `d_id`) VALUES (5, '张严', 'M', 'a@b.com', 1);
INSERT INTO `tbl_emp` (`emp_id`, `emp_name`, `gender`, `email`, `d_id`) VALUES (6, '何宽', 'F', 'a@b.com', 2);
INSERT INTO `tbl_emp` (`emp_id`, `emp_name`, `gender`, `email`, `d_id`) VALUES (7, '李微', 'M', 'a@b.com', 1);
INSERT INTO `tbl_emp` (`emp_id`, `emp_name`, `gender`, `email`, `d_id`) VALUES (8, '刘虹', 'M', 'a@b.com', 1);
INSERT INTO `tbl_emp` (`emp_id`, `emp_name`, `gender`, `email`, `d_id`) VALUES (9, '黄羽', 'F', 'a@b.com', 2);
INSERT INTO `tbl_emp` (`emp_id`, `emp_name`, `gender`, `email`, `d_id`) VALUES (10, '吴莉', 'F', 'a@b.com', 3);
INSERT INTO `tbl_emp` (`emp_id`, `emp_name`, `gender`, `email`, `d_id`) VALUES (11, '阿里', 'M', 'a@b.com', 3);


-- ----------------------------
-- Table structure for hawk_object
-- ----------------------------
DROP TABLE IF EXISTS `hawk_object`;
CREATE TABLE `hawk_object`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `relate_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hawk_object
-- ----------------------------
INSERT INTO `hawk_object` VALUES ('1', '测试1', NULL);
INSERT INTO `hawk_object` VALUES ('2', '测试2', '1');
INSERT INTO `hawk_object` VALUES ('3', '测试3', '1');
INSERT INTO `hawk_object` VALUES ('4', '测试4', '2');

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- Table structure for hawk_object_value
-- ----------------------------
DROP TABLE IF EXISTS `hawk_object_value`;
CREATE TABLE `hawk_object_value`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `relate_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `des` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hawk_object_value
-- ----------------------------
INSERT INTO `hawk_object_value` VALUES ('1', '1', '这个是测试1');
INSERT INTO `hawk_object_value` VALUES ('2', '2', '这个是测试2');
INSERT INTO `hawk_object_value` VALUES ('3', '3', '这个是测试3');
INSERT INTO `hawk_object_value` VALUES ('4', '4', '这个是是测试4');

SET FOREIGN_KEY_CHECKS = 1;

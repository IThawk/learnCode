CREATE TABLE `tbl_dept` (
  `dept_id` int(16) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


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

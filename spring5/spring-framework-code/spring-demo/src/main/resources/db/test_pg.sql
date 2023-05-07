/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.56.101
 Source Server Type    : PGSQL
 Source Server Version : 50734
 Source Host           : 192.168.56.101:3306
 Source Schema         : test

 Target Server Type    : PGSQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 02/07/2021 16:44:45
*/


-- ----------------------------
-- Table structure for depart
-- ----------------------------
DROP SEQUENCE if exists depart_id_seq;
CREATE SEQUENCE depart_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 999999999
START 1
CACHE 1;

DROP TABLE IF EXISTS depart;
CREATE TABLE depart  (
  id int4  NOT NULL DEFAULT nextval('depart_id_seq'::regclass),
  name varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;



-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP SEQUENCE if exists employee_id_seq;
CREATE SEQUENCE employee_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 999999999
START 1
CACHE 1;
DROP TABLE IF EXISTS employee;
CREATE TABLE employee  (
  id int4 NOT NULL DEFAULT nextval('employee_id_seq'::regclass),
  name varchar(255) DEFAULT NULL,
  age int4 DEFAULT NULL,
  PRIMARY KEY (id)
);


-- ----------------------------
-- Table structure for tbl_dept
-- ----------------------------
DROP SEQUENCE if exists tbl_dept_id_seq;
CREATE SEQUENCE tbl_dept_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 999999999
START 1
CACHE 1;

DROP TABLE IF EXISTS tbl_dept;
CREATE TABLE tbl_dept  (
  dept_id int4 NOT NULL DEFAULT nextval('tbl_dept_id_seq'::regclass),
  dept_name varchar(255)  DEFAULT NULL,
  PRIMARY KEY (dept_id)
) ;


-- ----------------------------
-- Table structure for tbl_emp
-- ----------------------------

DROP SEQUENCE if exists tbl_emp_id_seq;
CREATE SEQUENCE  tbl_emp_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 999999999
START 1
CACHE 1;
DROP TABLE IF EXISTS tbl_emp;
CREATE TABLE tbl_emp  (
  emp_id int4 NOT NULL DEFAULT nextval('tbl_emp_id_seq'::regclass),
  emp_name varchar(255)  DEFAULT NULL,
  gender varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  d_id int4 DEFAULT NULL,
  PRIMARY KEY (emp_id)
) ;




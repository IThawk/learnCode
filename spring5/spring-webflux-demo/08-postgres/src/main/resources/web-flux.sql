/*
 Navicat Premium Data Transfer

 Source Server         : local-qgsql
 Source Server Type    : PostgreSQL
 Source Server Version : 90426
 Source Host           : localhost:15432
 Source Catalog        : web-flux
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90426
 File Encoding         : 65001

 Date: 09/05/2022 15:57:46
*/


-- ----------------------------
-- Table structure for user_data
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_data";
CREATE TABLE "public"."user_data" (
  "id" int4 NOT NULL DEFAULT NULL,
  "first_name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL
)
;

-- ----------------------------
-- Records of user_data
-- ----------------------------
INSERT INTO "public"."user_data" VALUES (1, 'test', '123456');

-- ----------------------------
-- Table structure for user_email
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_email";
CREATE TABLE "public"."user_email" (
  "id" int4 NOT NULL DEFAULT NULL,
  "user_id" int4 DEFAULT NULL,
  "email" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL
)
;

-- ----------------------------
-- Records of user_email
-- ----------------------------
INSERT INTO "public"."user_email" VALUES (1, 1, '122rree');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_info";
CREATE TABLE "public"."user_info" (
  "id" int4 NOT NULL DEFAULT NULL,
  "first_name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL
)
;

-- ----------------------------
-- Primary Key structure for table user_data
-- ----------------------------
ALTER TABLE "public"."user_data" ADD CONSTRAINT "user_data_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_email
-- ----------------------------
ALTER TABLE "public"."user_email" ADD CONSTRAINT "user_email_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table user_info
-- ----------------------------
ALTER TABLE "public"."user_info" ADD CONSTRAINT "user_info_pkey" PRIMARY KEY ("id");

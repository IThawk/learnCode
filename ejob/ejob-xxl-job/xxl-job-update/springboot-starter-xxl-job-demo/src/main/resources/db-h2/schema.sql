DROP TABLE IF EXISTS orm_user;
CREATE TABLE orm_user
(
    id               INT4        NOT NULL PRIMARY KEY,
    name             VARCHAR(32) NOT NULL,
    password         VARCHAR(32) NOT NULL,
    salt             VARCHAR(32) NOT NULL,
    email            VARCHAR(32) NOT NULL,
    phone_number     VARCHAR(15) NOT NULL,
    status           INT4        NOT NULL DEFAULT 1,
    create_time      timestamp   NOT NULL DEFAULT NOW(),
    last_login_time  timestamp            DEFAULT NULL,
    last_update_time timestamp   NOT NULL DEFAULT NOW()
);
DROP SEQUENCE orm_user_id_seq;
CREATE SEQUENCE orm_user_id_seq;
ALTER TABLE orm_user
    ALTER id SET DEFAULT NEXTVAL('orm_user_id_seq');

COMMENT
ON COLUMN "public"."orm_user"."id" IS 'Id（主键）';
COMMENT
ON COLUMN "public"."orm_user"."name" IS '用户名';
COMMENT
ON COLUMN "public"."orm_user"."password" IS '加密后的密码';
COMMENT
ON COLUMN "public"."orm_user"."salt" IS '加密使用的盐';
COMMENT
ON COLUMN "public"."orm_user"."email" IS '邮箱';
COMMENT
ON COLUMN "public"."orm_user"."phone_number" IS '手机号码';
COMMENT
ON COLUMN "public"."orm_user"."status" IS '状态，-1：逻辑删除，0：禁用，1：启用';
COMMENT
ON COLUMN "public"."orm_user"."create_time" IS '创建时间';
COMMENT
ON COLUMN "public"."orm_user"."last_login_time" IS '上次登录时间';
COMMENT
ON COLUMN "public"."orm_user"."last_update_time" IS '上次更新时间';

DROP TABLE IF EXISTS orm_role;

CREATE TABLE orm_role
(
    id   INT4        NOT NULL PRIMARY KEY,
    name VARCHAR(32) NOT NULL
);

DROP SEQUENCE orm_role_id_seq;
CREATE SEQUENCE orm_role_id_seq;
ALTER TABLE orm_role
    ALTER id SET DEFAULT NEXTVAL('orm_role_id_seq');

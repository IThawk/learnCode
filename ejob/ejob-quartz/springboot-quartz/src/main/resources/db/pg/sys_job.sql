
CREATE TABLE if not EXISTS sys_job (
                                       id SERIAL  NOT NULL ,
                                       job_name varchar(512) NOT NULL ,
    job_group varchar(512) NOT NULL ,
    job_cron varchar(512) NOT NULL ,
    job_class_path varchar(1024) NOT NULL ,
    job_data_map varchar(1024) DEFAULT NULL ,
    job_status int NOT NULL ,
    job_describe varchar(1024) DEFAULT NULL ,
    PRIMARY KEY (id)
    );
COMMENT ON COLUMN sys_job.job_name IS '任务名称';
COMMENT ON COLUMN sys_job.job_group IS '任务组名';
COMMENT ON COLUMN sys_job.job_cron IS '时间表达式';
COMMENT ON COLUMN sys_job.job_class_path IS '类路径,全类型';
COMMENT ON COLUMN sys_job.job_data_map IS '传递map参数';
COMMENT ON COLUMN sys_job.job_status IS '状态:1启用 0停用';
COMMENT ON COLUMN sys_job.job_describe IS '任务功能描述';

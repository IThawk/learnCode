CREATE TABLE IF NOT EXISTS USER
(
    id               INT(40)  NOT NULL,
    name         VARCHAR(100) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);




-- CREATE TABLE IF NOT EXISTS JOB_EXECUTION_LOG
-- (
--     id               VARCHAR(40)  NOT NULL,
--     job_name         VARCHAR(100) NOT NULL,
--     task_id          VARCHAR(255) NOT NULL,
--     hostname         VARCHAR(255) NOT NULL,
--     ip               VARCHAR(50)  NOT NULL,
--     sharding_item    INT          NOT NULL,
--     execution_source VARCHAR(20)  NOT NULL,
--     failure_cause    VARCHAR(4000) NULL,
--     is_success       BOOLEAN      NOT NULL,
--     start_time       TIMESTAMP NULL,
--     complete_time    TIMESTAMP NULL,
--     PRIMARY KEY (id)
-- );
--
-- CREATE TABLE IF NOT EXISTS JOB_STATUS_TRACE_LOG
-- (
--     id               VARCHAR(40)  NOT NULL,
--     job_name         VARCHAR(100) NOT NULL,
--     original_task_id VARCHAR(255) NOT NULL,
--     task_id          VARCHAR(255) NOT NULL,
--     slave_id         VARCHAR(50)  NOT NULL,
--     source           VARCHAR(50)  NOT NULL,
--     execution_type   VARCHAR(20)  NOT NULL,
--     sharding_item    VARCHAR(100) NOT NULL,
--     state            VARCHAR(20)  NOT NULL,
--     message          VARCHAR(4000) NULL,
--     creation_time    TIMESTAMP NULL,
--     PRIMARY KEY (id)
-- );
-- CREATE INDEX IF NOT EXISTS TASK_ID_STATE_INDEX ON JOB_STATUS_TRACE_LOG (task_id, state);


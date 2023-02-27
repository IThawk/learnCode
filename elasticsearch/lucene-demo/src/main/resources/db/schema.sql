CREATE TABLE IF NOT EXISTS job_info
(
    id               VARCHAR(40)  NOT NULL,
    company_name         VARCHAR(100) NOT NULL,
    company_addr          VARCHAR(255) NOT NULL,
    company_info         VARCHAR(255) NOT NULL,
    job_name               VARCHAR(50)  NOT NULL,
    salary    INT          NOT NULL,
    job_info VARCHAR(4000)  NOT NULL,
    job_addr    VARCHAR(400) NULL,
    url       VARCHAR(255)     NOT NULL,
    time       TIMESTAMP NULL,
    PRIMARY KEY (id)
);

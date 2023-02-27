package com.ithawk.demo.elasticsearch.lucene.bean;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "job_info")
public class JobInfo {
    @Id
    private long id;
    private String companyName;
    private String companyAddr;
    private String companyInfo;
    private String jobName;
    private String jobAddr;
    private String jobInfo;
    private String url;
    private Date time;
    private int salary;
}
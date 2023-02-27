package com.ithawk.demo.elasticsearch.lucene.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ithawk.demo.elasticsearch.lucene.bean.JobInfo;
import org.apache.ibatis.annotations.Mapper;


//继承BaseMapper
@Mapper
public interface JobInfoMapper extends BaseMapper<JobInfo> {
}
package com.ithawk.demo.elasticsearch.lucene.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ithawk.demo.elasticsearch.lucene.bean.JobInfo;
import com.ithawk.demo.elasticsearch.lucene.dao.JobInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobInfoServiceImpl {

    @Autowired
    private JobInfoMapper jobInfoMapper;


    public JobInfo selectById(Long id) {
        return jobInfoMapper.selectById(id);
    }


    public List<JobInfo> selectAll() {
        QueryWrapper<JobInfo> queryWrapper = new QueryWrapper<>();
        return jobInfoMapper.selectList(queryWrapper);
    }
}
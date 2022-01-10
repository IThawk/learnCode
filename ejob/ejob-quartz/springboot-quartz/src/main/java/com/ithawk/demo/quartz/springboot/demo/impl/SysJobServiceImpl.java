package com.ithawk.demo.quartz.springboot.demo.impl;

import com.ithawk.demo.quartz.springboot.demo.entity.SysJob;
import com.ithawk.demo.quartz.springboot.demo.mapper.SysJobMapper;
import com.ithawk.demo.quartz.springboot.demo.service.ISysJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SysJobServiceImpl implements ISysJobService {
	@Autowired
	private SysJobMapper sysJobMapper;

	@Override
	public int getJobCount() {
		return sysJobMapper.getJobCount();
	}

	@Override
	public List<SysJob> querySysJobList(HashMap<String, String> map) {
		return sysJobMapper.querySysJobList(map);
	}

	@Override
	public int insertSelective(SysJob record) {
		return sysJobMapper.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return sysJobMapper.deleteByPrimaryKey(id);
	}

	@Override
	public SysJob selectByPrimaryKey(Integer id) {
		return sysJobMapper.selectByPrimaryKey(id);
	}

	@Override
	public SysJob selectByBean(SysJob bean) {
		return sysJobMapper.selectByBean(bean);
	}

	@Override
	public int updateByPrimaryKeySelective(SysJob bean) {
		return sysJobMapper.updateByPrimaryKeySelective(bean);
	}

}

package com.search.mgr.service.impl;

import com.search.mgr.dao.ReadBookPdMapper;
import com.search.mgr.model.ReadBookPd;
import com.search.mgr.service.ReadBookPdService;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.search.mgr.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by CodeGenerator on 2019/11/14.
 */
@Service
@Transactional
public class ReadBookPdServiceImpl extends AbstractService<ReadBookPd> implements ReadBookPdService {
	@Resource
	private ReadBookPdMapper readBookPdMapper;

	@Override
	public int getBookCount() {
		Condition condition = new Condition(ReadBookPd.class);
		Criteria criteria = condition.createCriteria();
		criteria.andEqualTo("status", 1);
		return readBookPdMapper.selectCountByCondition(condition);
	}

	@Override
	public List<ReadBookPd> getPageList(int page, int size) {
		int start = (page - 1) * size;
		return readBookPdMapper.getPageList(start, size);
	}

}

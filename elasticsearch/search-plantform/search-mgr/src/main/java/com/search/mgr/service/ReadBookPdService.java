package com.search.mgr.service;

import com.search.mgr.model.ReadBookPd;

import java.util.List;

import com.search.mgr.core.Service;

/**
 * Created by CodeGenerator on 2019/11/14.
 */
public interface ReadBookPdService extends Service<ReadBookPd> {

	public int getBookCount();

	public List<ReadBookPd> getPageList(int page, int size);
}

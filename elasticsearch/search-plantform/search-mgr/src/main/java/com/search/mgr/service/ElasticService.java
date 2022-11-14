package com.search.mgr.service;

import com.search.mgr.model.ReadBookPd;

import java.util.List;
import java.util.Map;

public interface ElasticService {

	public Boolean add(Map<String, Object> doc,String index, String type) throws Exception;

	public Boolean adds(List<Map<String, Object>> docs);
	
	public Boolean addBulkIn(List<Map<String, Object>> datas,String index,String type);
	
	public Boolean delete(String index);

	ReadBookPd findById(Integer id);



}

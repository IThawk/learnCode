package com.search.mgr.service;

public interface ReIndexService {

	/**
	 * 对books索引进行全量重建
	 * @return
	 */
	public boolean reIndexBooks();

}

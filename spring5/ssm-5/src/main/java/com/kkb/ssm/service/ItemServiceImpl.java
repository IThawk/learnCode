package com.kkb.ssm.service;

import com.kkb.ssm.mapper.ItemMapper;
import com.kkb.ssm.po.Item;
import com.kkb.ssm.po.ItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper mapper;
	
	@Override
	public List<Item> queryItemList() {
		ItemExample example = new ItemExample();
		List<Item> list = mapper.selectByExample(example );
		return list;
	}

}

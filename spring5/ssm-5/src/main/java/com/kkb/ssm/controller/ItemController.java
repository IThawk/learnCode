package com.kkb.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kkb.ssm.po.Item;
import com.kkb.ssm.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService service;

	@RequestMapping("queryItem")
	@ResponseBody
	public List<Item> queryItem(){
		return service.queryItemList();
	}
}

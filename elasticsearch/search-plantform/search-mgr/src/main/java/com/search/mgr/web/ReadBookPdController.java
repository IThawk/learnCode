package com.search.mgr.web;

import com.search.mgr.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.search.mgr.core.Result;
import com.search.mgr.core.ResultGenerator;
import com.search.mgr.model.ReadBookPd;
import com.search.mgr.service.ReIndexService;
import com.search.mgr.service.ReadBookPdService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/11/14.
*/
@Controller
@RequestMapping("/read/book/pd")
public class ReadBookPdController {
    @Resource
    private ReadBookPdService readBookPdService;

    @Resource
    ReIndexService indexService;

    @Autowired
    ElasticService elasticService;
    
    @PostMapping
    public Result add(@RequestBody ReadBookPd readBookPd) {
        readBookPdService.save(readBookPd);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        readBookPdService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody ReadBookPd readBookPd) {
        readBookPdService.update(readBookPd);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ReadBookPd readBookPd = readBookPdService.findById(id);
        return ResultGenerator.genSuccessResult(readBookPd);
    }
    @GetMapping("/indexall")
    public Result indexAll() {
    	indexService.reIndexBooks();
    	return ResultGenerator.genSuccessResult();
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ReadBookPd> list = readBookPdService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/es/{id}")
    public Result getFromEs(@PathVariable Integer id) {
        ReadBookPd readBookPd = elasticService.findById(id);
        return ResultGenerator.genSuccessResult(readBookPd);
    }
}

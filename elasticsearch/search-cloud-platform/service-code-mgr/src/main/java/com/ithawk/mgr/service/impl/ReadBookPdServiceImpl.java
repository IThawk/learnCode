package com.ithawk.mgr.service.impl;

import com.ithawk.mgr.dao.ReadBookPdMapper;
import com.ithawk.mgr.model.ReadBookPd;
import com.ithawk.mgr.service.ReadBookPdService;
import com.ithawk.mgr.service.impl.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2021/12/03.
 */
@Service
@Transactional
public class ReadBookPdServiceImpl extends AbstractService<ReadBookPd> implements ReadBookPdService {
    @Resource
    private ReadBookPdMapper readBookPdMapper;

}

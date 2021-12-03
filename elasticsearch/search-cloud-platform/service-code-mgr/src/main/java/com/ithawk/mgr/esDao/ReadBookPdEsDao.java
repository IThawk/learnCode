package com.ithawk.mgr.esDao;


import com.ithawk.mgr.model.ReadBookPd;
import com.ithawk.mgr.esDao.AbstractElasticsearchDao;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;


/**
* Created by CodeGenerator on 2021/12/03.
* ES操作类，如需其他接口参考官方文档自行添加。
*/
@Component
public class ReadBookPdEsDao extends AbstractElasticsearchDao<ReadBookPd,ReadBookPd> {

     @PostConstruct
     public void init() {
           clazz = ReadBookPd.class;
           getBaseModelInfo();
     }

}




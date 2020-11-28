package com.ithawk.demo.spring.orm.demo.dao;

import com.ithawk.demo.spring.orm.framework.BaseDaoSupport;
import com.ithawk.demo.spring.orm.framework.QueryRule;
import com.ithawk.demo.thread.orm.demo.entity.Member;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.core.common.Page;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Tom.
 */
@Repository
public class MemberDao extends BaseDaoSupport<Member,Long> {

    @Override
    protected String getPKColumn() {
        return "id";
    }

    @Resource(name="dataSource")
    public void setDataSource(DataSource dataSource){
        super.setDataSourceReadOnly(dataSource);
        super.setDataSourceWrite(dataSource);
    }


    public List<Member> selectAll() throws  Exception{
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andLike("name","Tom%");
        return super.select(queryRule);
    }
}

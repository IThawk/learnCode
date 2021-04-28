package com.ithawk.springboot.demo.orm.mybatis.mapper;

import com.ithawk.springboot.demo.orm.mybatis.entity.OrmUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrmUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrmUser record);

    OrmUser selectByPrimaryKey(Integer id);

    List<OrmUser> selectAll();

    int updateByPrimaryKey(OrmUser record);
}
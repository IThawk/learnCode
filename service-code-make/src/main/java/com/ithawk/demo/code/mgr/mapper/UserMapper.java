package com.ithawk.demo.code.mgr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ithawk.demo.code.mgr.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.logging.LoggerFactory;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

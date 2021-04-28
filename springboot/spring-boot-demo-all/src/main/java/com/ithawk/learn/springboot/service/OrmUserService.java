package com.ithawk.learn.springboot.service;

import com.ithawk.learn.springboot.common.ExcelMaker;
import com.ithawk.learn.springboot.entity.OrmUser;
import com.ithawk.learn.springboot.entity.User;

import java.util.List;

/**
 *
 * @author IThawk
 * @version V1.0
 * @description:
 * @date 2020-04-11 20:51
 */
public interface OrmUserService  extends ExcelMaker<OrmUser> {

    int addOrmUser(OrmUser user);

    List<OrmUser> selectAllUser();
}

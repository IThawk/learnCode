package com.ithawk.demo.code.mgr.service;

import com.github.pagehelper.PageInfo;
import com.ithawk.demo.code.mgr.bean.User;
import com.ithawk.demo.code.mgr.bean.vo.UserVO;

import java.util.List;

public interface UserService {
    /**
     * 添加
     */
    int add(User user);

    /**
     * 删除
     */
    int delete(Integer userId);

    /**
     * 更新
     */
    int update(User user);

    /**
     * 分页查询
     */
    PageInfo<User> findByPage(Integer pageNum, Integer pageSize, UserVO vo);

    /**
     * 查询所有
     */
    List<User> findAll();

    /**
     * 根据id查询
     */
    User findById(Integer userId);
}

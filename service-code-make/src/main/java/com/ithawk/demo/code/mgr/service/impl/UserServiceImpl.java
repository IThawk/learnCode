package com.ithawk.demo.code.mgr.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ithawk.demo.code.mgr.bean.User;
import com.ithawk.demo.code.mgr.bean.vo.UserVO;
import com.ithawk.demo.code.mgr.mapper.UserMapper;
import com.ithawk.demo.code.mgr.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int add(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int delete(Integer userId) {
        return userMapper.deleteById(userId);
    }

    @Override
    public int update(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public PageInfo<User> findByPage(Integer pageNum, Integer pageSize, UserVO vo) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            userMapper.selectList(Wrappers.<User>query());
        });
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectList(Wrappers.<User>query(null));
    }

    @Override
    public User findById(Integer userId) {
        return userMapper.selectById(userId);
    }
}

package com.ithawk.demo.code.mgr.controller;

import com.github.pagehelper.PageInfo;
import com.ithawk.demo.code.mgr.bean.User;
import com.ithawk.demo.code.mgr.bean.vo.UserVO;
import com.ithawk.demo.code.mgr.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * 用户控制器
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 分页查询
     */
    @RequestMapping("/list")
    public String page(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       UserVO vo, Model model) {
        PageInfo<User> page = userService.findByPage(pageNum, pageSize, vo);
        model.addAttribute("page", page);
        return "user/list";
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{userId}")
    public String delete(@PathVariable("userId")Integer userId) {
        userService.delete(userId);
        return "redirect:/user/list";
    }

    /**
     * 批量删除
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(@RequestParam("id") Integer[] ids) {
        for(Integer index : ids) {
            userService.delete(index);
        }

        return "redirect:/user/list";
    }

    /**
     * 跳转至更新页面；根据id查询用户信息
     */
    @RequestMapping("/toUpdate/{userId}")
    public String toUpdate(@PathVariable("userId")Integer userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);

        return "user/update";
    }

    /**
     * 更新用户信息
     */
    @RequestMapping("/update")
    public String update(User user, Model model) {
        User exist = userService.findById(user.getUserId());
        exist.setRealName(user.getRealName());
        exist.setUserSex(user.getUserSex());
        exist.setBirthday(user.getBirthday());
        exist.setUserPhone(user.getUserPhone());
        exist.setUserEmail(user.getUserEmail());
        exist.setUsername(user.getUsername());
        exist.setPassword(user.getPassword());

        int update = userService.update(exist);
        model.addAttribute("user", exist);
        return "user/update";
    }

    /**
     * 跳转至添加页面
     */
    @RequestMapping("/toAdd")
    public String toUpdate(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/add";
    }
    /**
     * 添加用户
     */
    @RequestMapping("/add")
    public String add(User user, Model model) {
        int add = userService.add(user);
        return "user/add";
    }

}

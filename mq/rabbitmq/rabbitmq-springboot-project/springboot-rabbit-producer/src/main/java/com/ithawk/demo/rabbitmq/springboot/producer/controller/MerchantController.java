package com.ithawk.demo.rabbitmq.springboot.producer.controller;


import com.ithawk.demo.rabbitmq.springboot.producer.entity.Merchant;
import com.ithawk.demo.rabbitmq.springboot.producer.service.MerchantService;
import com.ithawk.demo.rabbitmq.springboot.producer.util.Constant;
import com.ithawk.demo.rabbitmq.springboot.producer.util.LayuiData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Api(value = "MerchantController")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    /**
     * 查询商户列表
     *
     * @return
     */
    @GetMapping("/getMerchantList")
    @ApiOperation("查询商户列表")
    @ResponseBody
    public LayuiData getMerchantList(@RequestParam(value = "name") String name, @RequestParam(value = "page") int page, @RequestParam(value = "limit") int limit) {
        if (page >= 1) {
            page = (page - 1) * limit;
        }
        LayuiData layuiData = new LayuiData();
        List<Merchant> merchantList = merchantService.getMerchantList(name, page, limit);
        int count = merchantService.getMerchantCount();
        layuiData.setCode(0);
        layuiData.setCount(count);
        layuiData.setMsg("数据请求成功");
        layuiData.setData(merchantList);
        return layuiData;
    }

    /**
     * 去新增商户界面
     *
     * @return
     */
    @RequestMapping("/toMerchant")
    @ApiOperation("去新增商户界面")
    public String addMerchant() {
        return "merchantAdd";
    }

    /**
     * 新增商户
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping("/merchantAdd")
    @Transactional
    @ApiOperation("新增商户")
    @ResponseBody
    public Integer merchantAdd( String name, String address, String accountNo, String accountName) {
        Merchant merchant = new Merchant();

        merchant.setName(name);
        merchant.setAddress(address);
        merchant.setAccountNo(accountNo);
        merchant.setAccountName(accountName);
        merchant.setState(Constant.MERCHANT_STATE.ACITVE);
        int num = merchantService.add(merchant);
        return num;
    }

    @RequestMapping("/merchantList")
    @ApiOperation("merchantList")
    public String merchantList1() {
        return "merchantListPage";
    }

    /**
     * 根据id删除商户
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ApiOperation("根据id删除商户")
    @ResponseBody
    public Integer delete(Integer id) {

        int num = merchantService.delete(id);

        return num;
    }

    /**
     * 去查看界面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toDetail")
    @ApiOperation("去查看界面")
    public String toDetail(Integer id, Model model) {

        Merchant merchant = merchantService.getMerchantById(id);
        model.addAttribute("merchant", merchant);
        return "merchantDetail";
    }

    /**
     * 去修改界面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toUpdate")
    @ApiOperation("去修改界面")
    public String toUpdate(Integer id, Model model) {

        Merchant merchant = merchantService.getMerchantById(id);
        model.addAttribute("merchant", merchant);
        return "merchantUpdate";
    }

    /**
     * 根据id修改商户信息
     *
     * @return
     */
    @RequestMapping("/merchantUpdate")
    @Transactional
    @ResponseBody
    @ApiOperation("根据id修改商户信息")
    public Integer merchantUpdate(Integer id, String name, String address, String accountNo, String accountName) {
        Merchant merchant = new Merchant();
        merchant.setId(id);
        merchant.setName(name);
        merchant.setAddress(address);
        merchant.setAccountNo(accountNo);
        merchant.setAccountName(accountName);
        int num = merchantService.update(merchant);
        return num;
    }

    /**
     * 变更商户状态
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/changeState")
    @ApiOperation("变更商户状态")
    @ResponseBody
    public String changeState(@RequestParam(value = "id") String idStr) throws Exception {
        String errmsg = "";
        if (null == idStr || "".equals(idStr))
            return "商户号不能为空";
        int id = Integer.parseInt(idStr);

        // 校验
        Merchant result = merchantService.getMerchantById(id);
        if (null == result) {
            return "编号为" + id + "的商户不存在！";
        }

        Merchant updateBean = new Merchant();
        updateBean.setId(id);
        //如果是现在是启用，则停用
        if (Constant.MERCHANT_STATE.ACITVE.equals(result.getState())) {
            updateBean.setState("0");
        } else {
            updateBean.setState("1");
        }

        int num = merchantService.updateState(updateBean);
        // 1表示成功
        if (num == 1) {
            return "1";
        } else {
            return "更新商户状态失败";
        }

    }
}

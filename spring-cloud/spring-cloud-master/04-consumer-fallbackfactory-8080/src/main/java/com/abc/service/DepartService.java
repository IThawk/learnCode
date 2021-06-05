package com.abc.service;

import com.abc.bean.Depart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 关于Feign的说明：
// 1)Feign接口名一般是与业务接口名相同的，但不是必须的
// 2)Feign接口中的方法名一般也是与业务接口方法名相同，但也不是必须的
// 3)Feign接口中的方法返回值类型，方法参数要求与业务接口中的相同
// 4)接口上与方法上的Mapping的参数URI要与提供者处理器相应方法上的Mapping的URI相同

// 指定当前为Feign客户端，参数为提供者的微服务名称
// fallbackFactory用于指定当前Feign接口的服务降级类
@FeignClient(value = "abcmsc-provider-depart", fallbackFactory = DepartFallbackFactory.class)
@RequestMapping("/provider/depart")
public interface DepartService {
    @PostMapping("/save")
    boolean saveDepart(@RequestBody Depart depart);

    @DeleteMapping("/del/{id}")
    boolean removeDepartById(@PathVariable("id") Integer id);

    @PutMapping("/update")
    boolean modifyDepart(@RequestBody Depart depart);

    @GetMapping("/get/{id}")
    Depart getDepartById(@PathVariable("id") Integer id);

    @GetMapping("/list")
    List<Depart> listAllDeparts();
}

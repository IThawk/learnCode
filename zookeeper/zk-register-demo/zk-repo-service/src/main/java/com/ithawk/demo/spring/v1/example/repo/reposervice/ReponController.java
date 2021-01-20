package com.ithawk.demo.spring.v1.example.repo.reposervice;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReponController {

    @PutMapping("/repo/{pid}")
    public void repo(@PathVariable("pid") String pid){
        System.out.println("扣减商品库存："+pid);
    }
}

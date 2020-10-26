package com.ithawk.demo.rabbitmq.springboot.producer.mapper;

import com.ithawk.demo.rabbitmq.springboot.producer.entity.Merchant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MerchantMapper {

   Merchant getMerchantById(Integer sid);

   List<Merchant> getMerchantList(@Param(value = "name") String name,@Param(value = "page")  int page,@Param(value = "limit")  int limit);

    int add(Merchant merchant);

    int update(Merchant merchant);

    int updateState(Merchant merchant);

    int delete(@Param(value = "sid")  Integer sid);

    int getMerchantCount();
}
package com.baizhi.test;

import com.baizhi.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

public class TestElasticSearchOptions  extends ElasticsearchDemoApplicationTests{


    private  final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public TestElasticSearchOptions(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    //创建索引index&创建映射mapping 并索引一条文件
    //保存 & 更新 id 存在更新  id 不存在保存
    @Test
    public void testIndex(){
        Product product = new Product();
        product.setId(1);
        product.setTitle("大豫竹干吃面");
        product.setPrice(1.9);
        product.setDescription("大豫竹真的很不错!!!!");
        elasticsearchOperations.save(product);
    }

    @Test
    public void testDelete(){
        Product product = new Product();
        product.setId(1);
        elasticsearchOperations.delete(product);
    }

    @Test
    public void testGet(){
        Product product = elasticsearchOperations.get("1", Product.class);
        System.out.println(product.getId());
        System.out.println(product.getPrice());
        System.out.println(product.getTitle());
        System.out.println(product.getDescription());
    }
}

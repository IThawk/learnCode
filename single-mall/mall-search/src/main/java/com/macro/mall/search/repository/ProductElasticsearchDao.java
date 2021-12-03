package com.macro.mall.search.repository;

import com.macro.mall.search.domain.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * 商品ES操作类
 */
@Component
public class ProductElasticsearchDao extends AbstractElasticsearchDao<EsProduct> {

    @PostConstruct
    public void init() {
        clazz = EsProduct.class;
        if (clazz.isAnnotationPresent(Document.class)) {
            Document document = (Document) clazz.getAnnotation(Document.class);
            index = document.indexName();
            if (keyWordMap==null){
                keyWordMap= new HashMap<>(clazz.getFields().length);
            }
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(org.springframework.data.elasticsearch.annotations.Field.class)) {
                    org.springframework.data.elasticsearch.annotations.Field field1 = (org.springframework.data.elasticsearch.annotations.Field) field.getAnnotation(org.springframework.data.elasticsearch.annotations.Field.class);

                    if (field1.type()== FieldType.Text){
                        keyWordMap.put(field.getName(), "");
                    }

                }
            }

        }


    }

}

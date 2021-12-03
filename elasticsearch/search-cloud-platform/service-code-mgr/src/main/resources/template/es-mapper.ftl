package ${basePackage}.esDao;


import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.esDao.AbstractElasticsearchDao;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;


/**
* Created by ${author} on ${date}.
* ES操作类，如需其他接口参考官方文档自行添加。
*/
@Component
public class ${modelNameUpperCamel}EsDao extends AbstractElasticsearchDao<${modelNameUpperCamel},${modelNameUpperCamel}> {

     @PostConstruct
     public void init() {
           clazz = ${modelNameUpperCamel}.class;
           getBaseModelInfo();
     }

}




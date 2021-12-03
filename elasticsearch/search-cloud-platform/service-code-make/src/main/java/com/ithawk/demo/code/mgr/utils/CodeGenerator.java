package com.ithawk.demo.code.mgr.utils;

import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 */
public class CodeGenerator {
    //JDBC配置，请修改为你项目的实际配置
    private static final String JDBC_URL = "jdbc:mysql://192.168.56.101:3306/es-test";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

//    private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径


    //这个地方先写死
    private static final String PROJECT_PATH ="D:/workspace/language/make";


    //这个地方先写死
    private static final String CODE_PROJECT_PATH ="D:/workspace/language/github/learnCode/elasticsearch/search-cloud-platform/service-code-make";

    private static final String TEMPLATE_FILE_PATH = CODE_PROJECT_PATH + "/src/main/resources/template";//模板位置

    private static final String JAVA_PATH = "/src/main/java"; //java文件路径
    private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径


    private static final String PACKAGE_PATH_BASE = packageConvertPath(ProjectConstant.BASE_PACKAGE);

    //生成的Service存放路径
    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(ProjectConstant.SERVICE_PACKAGE);

    //生成的Service实现存放路径
    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(ProjectConstant.SERVICE_IMPL_PACKAGE);


    //生成的core实现存放路径
    private static final String PACKAGE_PATH_CORE = packageConvertPath(ProjectConstant.CORE_PACKAGE);

    //生成的config存放路径
    private static final String PACKAGE_PATH_CONFIG = packageConvertPath(ProjectConstant.CONFIG_PACKAGE);

    //生成的Controller存放路径
    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(ProjectConstant.CONTROLLER_PACKAGE);

    //生成的Controller存放路径
    private static final String PACKAGE_MAPPER_INTERFACE_REFERENCE = packageConvertPath(ProjectConstant.MAPPER_INTERFACE_REFERENCE);

    //生成的EsMapper存放路径
    private static final String PACKAGE_PATH_ES_MAPPER= packageConvertPath(ProjectConstant.ES_MAPPER_PACKAGE);

    private static final String AUTHOR = "CodeGenerator";//@author

    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//@date

    public static void main(String[] args) {
//        genCode("输入表名");
        genCore();
    	genCodeByCustomModelName("read_book_pd", "ReadBookPd");

    }

    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。
     * 如输入表名称 "t_user_detail" 将生成 TUserDetail、TUserDetailMapper、TUserDetailService ...
     * @param tableNames 数据表名称...
     */
    public static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            genCodeByCustomModelName(tableName, null);
        }
    }

    /**
     * 通过数据表名称，和自定义的 Model 名称生成代码
     * 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User" 将生成 User、UserMapper、UserService ...
     * @param tableName 数据表名称
     * @param modelName 自定义的 Model 名称
     */
    public static void genCodeByCustomModelName(String tableName, String modelName) {
        genService(tableName, modelName);
        genController(tableName, modelName);
        genEsMapper(tableName, modelName);
        genModelAndMapper(tableName, modelName);

    }


    public static void genModelAndMapper(String tableName, String modelName) {
        Context context = new Context(ModelType.FLAT);
//        context.setCommentGeneratorConfiguration(new MyGeneratorConfiguration());
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", ProjectConstant.MAPPER_INTERFACE_REFERENCE);
        context.addPluginConfiguration(pluginConfiguration);

        context.setCommentGeneratorConfiguration(new CommentGeneratorConfiguration(){

//            @Override
//            public void setConfigurationType(String configurationType) {
//                super.setConfigurationType("com.ithawk.demo.code.mgr.utils.MyCommentGenerator");
//            }

            @Override
            public String getConfigurationType() {
                return "com.ithawk.demo.code.mgr.utils.MyCommentGenerator";
            }
        });

//        <!--optional,旨在创建class时，对注释进行控制-->
//        <commentGenerator>
//            <property name="suppressDate" value="true" />
//            <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
//            <property name="suppressAllComments" value="true" />
//        </commentGenerator>
        Properties properties = new Properties();
        properties.setProperty("suppressAllComments","true");
        context.getCommentGenerator().addConfigurationProperties(properties);

        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(ProjectConstant.MODEL_PACKAGE);

        //添加父类
        javaModelGeneratorConfiguration.addProperty("rootClass",ProjectConstant.CORE_PACKAGE+".EsBaseQuery");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);



        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper");
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(ProjectConstant.MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        if (StringUtils.isNotEmpty(modelName))tableConfiguration.setDomainObjectName(modelName);
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);


        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();

            config.addContext(context);
            config.validate();
            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<String>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        if (StringUtils.isEmpty(modelName)) modelName = tableNameConvertUpperCamel(tableName);
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }

    public static void genService(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", ProjectConstant.BASE_PACKAGE);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data,
                    new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

            File file1 = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data,
                    new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    public static void genController(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", ProjectConstant.BASE_PACKAGE);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("controller-restful.ftl").process(data, new FileWriter(file));
//            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }

    public static void genEsMapper(String tableName, String modelName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", ProjectConstant.BASE_PACKAGE);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_ES_MAPPER + modelNameUpperCamel + "EsDao.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("es-mapper.ftl").process(data,
                    new FileWriter(file));
            System.out.println(modelNameUpperCamel + "EsDao.java 生成成功");
//
//            File file1 = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL + modelNameUpperCamel + "ServiceImpl.java");
//            if (!file1.getParentFile().exists()) {
//                file1.getParentFile().mkdirs();
//            }
//            cfg.getTemplate("service-impl.ftl").process(data,
//                    new FileWriter(file1));
//            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    public static void genCore() {
        try {
            freemarker.template.Configuration cfg = getConfiguration();

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = "";
//            data.put("modelNameUpperCamel", modelNameUpperCamel);
//            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("basePackage", ProjectConstant.BASE_PACKAGE);

            File file = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE + "Mapper.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("abstract-mapper.ftl").process(data,
                    new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Mapper.java 生成成功");

            File service = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE  + "Service.java");
            if (!service.getParentFile().exists()) {
                service.getParentFile().mkdirs();
            }
            cfg.getTemplate("abstract-service.ftl").process(data,
                    new FileWriter(service));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");


            File abstractService = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE_IMPL  + "AbstractService.java");
            if (!abstractService.getParentFile().exists()) {
                abstractService.getParentFile().mkdirs();
            }
            cfg.getTemplate("abstract-service-impl.ftl").process(data,
                    new FileWriter(abstractService));
            System.out.println(modelNameUpperCamel + "AbstractService.java 生成成功");

            File abstractElasticsearchDao = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_ES_MAPPER  + "AbstractElasticsearchDao.java");
            if (!abstractElasticsearchDao.getParentFile().exists()) {
                abstractElasticsearchDao.getParentFile().mkdirs();
            }
            cfg.getTemplate("es-abstract-mapper.ftl").process(data,
                    new FileWriter(abstractElasticsearchDao));
            System.out.println(modelNameUpperCamel + "AbstractElasticsearchDao.java 生成成功");


            File esDocument = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "EsDocument.java");
            if (!esDocument.getParentFile().exists()) {
                esDocument.getParentFile().mkdirs();
            }
            cfg.getTemplate("es-document.ftl").process(data,
                    new FileWriter(esDocument));
            System.out.println(modelNameUpperCamel + "esDocument.java 生成成功");



            File esFiled = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "EsFiled.java");
            if (!esFiled.getParentFile().exists()) {
                esFiled.getParentFile().mkdirs();
            }
            cfg.getTemplate("es-filed.ftl").process(data,
                    new FileWriter(esFiled));
            System.out.println(modelNameUpperCamel + "EsFiled.java 生成成功");


            File esFiledType = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "EsFieldType.java");
            if (!esFiledType.getParentFile().exists()) {
                esFiledType.getParentFile().mkdirs();
            }
            cfg.getTemplate("es-filed-type.ftl").process(data,
                    new FileWriter(esFiledType));
            System.out.println(modelNameUpperCamel + "EsFieldType.java 生成成功");



            File searchTools = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "SearchTools.java");
            if (!searchTools.getParentFile().exists()) {
                searchTools.getParentFile().mkdirs();
            }
            cfg.getTemplate("es-tool.ftl").process(data,
                    new FileWriter(searchTools));
            System.out.println(modelNameUpperCamel + "SearchTools.java 生成成功");


            File esBaseQuery = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "EsBaseQuery.java");
            if (!esBaseQuery.getParentFile().exists()) {
                esBaseQuery.getParentFile().mkdirs();
            }
            cfg.getTemplate("es-base-query.ftl").process(data,
                    new FileWriter(esBaseQuery));
            System.out.println(modelNameUpperCamel + "EsBaseQuery.java 生成成功");



            File CommonEntity = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "CommonEntity.java");
            if (!CommonEntity.getParentFile().exists()) {
                CommonEntity.getParentFile().mkdirs();
            }
            cfg.getTemplate("common-entity.ftl").process(data,
                    new FileWriter(CommonEntity));
            System.out.println(modelNameUpperCamel + "CommonEntity.java 生成成功");




            File ResponseData = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "ResponseData.java");
            if (!ResponseData.getParentFile().exists()) {
                ResponseData.getParentFile().mkdirs();
            }
            cfg.getTemplate("response-data.ftl").process(data,
                    new FileWriter(ResponseData));
            System.out.println(modelNameUpperCamel + "ResponseData.java 生成成功");



            File ResultEnum = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "ResultEnum.java");
            if (!ResultEnum.getParentFile().exists()) {
                ResultEnum.getParentFile().mkdirs();
            }
            cfg.getTemplate("result-enum.ftl").process(data,
                    new FileWriter(ResultEnum));
            System.out.println(modelNameUpperCamel + "ResultEnum.java 生成成功");



            File TipsEnum = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "TipsEnum.java");
            if (!TipsEnum.getParentFile().exists()) {
                TipsEnum.getParentFile().mkdirs();
            }
            cfg.getTemplate("tips-enum.ftl").process(data,
                    new FileWriter(TipsEnum));
            System.out.println(modelNameUpperCamel + "TipsEnum.java 生成成功");


            File Result = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "Result.java");
            if (!Result.getParentFile().exists()) {
                Result.getParentFile().mkdirs();
            }
            cfg.getTemplate("result.ftl").process(data,
                    new FileWriter(Result));
            System.out.println(modelNameUpperCamel + "Result.java 生成成功");


            File ResultGenerator = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "ResultGenerator.java");
            if (!ResultGenerator.getParentFile().exists()) {
                ResultGenerator.getParentFile().mkdirs();
            }
            cfg.getTemplate("result-generator.ftl").process(data,
                    new FileWriter(ResultGenerator));
            System.out.println(modelNameUpperCamel + "ResultGenerator.java 生成成功");


            File ResultCode = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "ResultCode.java");
            if (!ResultCode.getParentFile().exists()) {
                ResultCode.getParentFile().mkdirs();
            }
            cfg.getTemplate("result-code.ftl").process(data,
                    new FileWriter(ResultCode));
            System.out.println(modelNameUpperCamel + "ResultCode.java 生成成功");


            File SwaggerConfig = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONFIG  + "Swagger2Config.java");
            if (!SwaggerConfig.getParentFile().exists()) {
                SwaggerConfig.getParentFile().mkdirs();
            }
            cfg.getTemplate("swagger-config.ftl").process(data,
                    new FileWriter(SwaggerConfig));
            System.out.println(modelNameUpperCamel + "SwaggerConfig.java 生成成功");

            File ElasticsearchConfig = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONFIG  + "ElasticsearchConfig.java");
            if (!ElasticsearchConfig.getParentFile().exists()) {
                ElasticsearchConfig.getParentFile().mkdirs();
            }
            cfg.getTemplate("elasticsearch-config.ftl").process(data,
                    new FileWriter(ElasticsearchConfig));
            System.out.println(modelNameUpperCamel + "ElasticsearchConfig.java 生成成功");


            File ServiceApplication = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_BASE  + "ServiceApplication.java");
            if (!ServiceApplication.getParentFile().exists()) {
                ServiceApplication.getParentFile().mkdirs();
            }
            cfg.getTemplate("springboot-run.ftl").process(data,
                    new FileWriter(ServiceApplication));
            System.out.println(modelNameUpperCamel + "ServiceApplication.java 生成成功");

            File ServiceException = new File(PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CORE  + "ServiceException.java");
            if (!ServiceException.getParentFile().exists()) {
                ServiceException.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-exception.ftl").process(data,
                    new FileWriter(ServiceException));
            System.out.println(modelNameUpperCamel + "ServiceException.java 生成成功");


            File bootstrap = new File(PROJECT_PATH + RESOURCES_PATH  + "/bootstrap.yml");
            if (!bootstrap.getParentFile().exists()) {
                bootstrap.getParentFile().mkdirs();
            }
            cfg.getTemplate("resources/bootstrap-yml.ftl").process(data,
                    new FileWriter(bootstrap));
            System.out.println(modelNameUpperCamel + "bootstrap.yml 生成成功");

            File maven = new File(PROJECT_PATH  + "/maven.xml");
            if (!maven.getParentFile().exists()) {
                maven.getParentFile().mkdirs();
            }
            cfg.getTemplate("resources/maven.ftl").process(data,
                    new FileWriter(maven));
            System.out.println(modelNameUpperCamel + "maven.xml 生成成功");

        } catch (Exception e) {
            throw new RuntimeException("生成Core失败", e);
        }
    }


    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

    }

    private static String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();//兼容使用大写的表名
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    private static String modelNameConvertMappingPath(String modelName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
        return tableNameConvertMappingPath(tableName);
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

}

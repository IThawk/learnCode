package com.xxl.job.admin.controller;

import com.xxl.job.admin.core.util.JacksonUtil;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.MetaMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/xxl-job-admin/tools")
public class ToolsController {
    private static GroovyClassLoader groovyClassLoader = new GroovyClassLoader();


    @PostMapping("/groovy")
    @ResponseBody
    public static Map<String, Object> runGroovy(@RequestBody Map<String, Object> stringObjectMap) {
        Map<String, Object> re = new HashMap<>();
        try {
//            Map<String, Object> stringObjectMap = JacksonUtil.readValue(groovyStr, Map.class);

            if (StringUtils.isBlank(stringObjectMap.get("executor").toString())) {
                return re;
            }
            Map<String, Object> executorParam = new HashMap<>();
            if (!Objects.isNull(stringObjectMap.get("executorParam"))) {
                if (!StringUtils.isBlank(stringObjectMap.get("executorParam").toString())){
                    executorParam = JacksonUtil.readValue((String) stringObjectMap.get("executorParam"), Map.class) ;
                }

            }
            Class<?> groovyClass = null;
            groovyClass = groovyClassLoader.parseClass(stringObjectMap.get("executor").toString());
            GroovyObject service = (GroovyObject) groovyClass.newInstance();
            re = (Map<String, Object>) service.invokeMethod("groovyMethod", executorParam);
            return re;
        } catch (Exception e) {
            re.put("runError", ExceptionUtils.getStackTrace(e));
            return re;

        }
    }

    public static void main(String[] args) {
        String s = "import java.util.Date;\n" +
                "import java.text.SimpleDateFormat;\n" +
                "\n" +
                "def groovyMethod(Map<String,Object> linkedHashMap) {\n" +
                "    if (linkedHashMap.containsKey('interRequestMap')) {\n" +
                "        def interRequestMap = linkedHashMap.get('interRequestMap')\n" +
                "        def productContractEntity = interRequestMap.get('productContractEntity')\n" +
                "        def actuals = productContractEntity.get('actuals')\n" +
                "        def actualsOne = actuals.get(0)\n" +
                "        def actual = actualsOne.get('actual')\n" +
                "\t\tdef startTime = actual.get('startTime')\n" +
                "\t\tdef plcSales = actual.get('plcSales')\n" +
                "\t\tdef plcSale = plcSales.get(0)\n" +
                "\t\tdef makeCom = actual.get('makeCom')\n" +
                "\t\tdef businessOffice = actual.get('businessOffice')\n" +
                "\t\t// 日期格式化\n" +
                "        SimpleDateFormat sdf = new SimpleDateFormat(\"yyyy-MM-dd'T'HH:mm:ss.SSSZ\")\n" +
                "        def today = new Date()\n" +
                "        def start = sdf.parse(startTime)\n" +
                "\t\tdef lastWeek = start + 7\n" +
                "\t\t\n" +
                "\t\tif(!businessOffice.startsWith('500')&&today.after(lastWeek)){\n" +
                "\t\t   Map<String, Object> map = new HashMap<String, Object>();\n" +
                "\t\t   map.put('errorCode','9999')\n" +
                "\t\t   map.put('errorDesc','未通过倒签规则校验,请检查')\n" +
                "\t\t   return map\n" +
                "\t\t  \n" +
                "\t\t}\n" +
                "        return linkedHashMap\n" +
                "    }\n" +
                "}\n";
        Map<String,Object> stringObjectMap = new HashMap<>();
        runGroovy(stringObjectMap);
    }
}

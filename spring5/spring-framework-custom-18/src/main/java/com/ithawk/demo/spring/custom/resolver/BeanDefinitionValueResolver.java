package com.ithawk.demo.spring.custom.resolver;

import com.ithawk.demo.spring.custom.factory.BeanFactory;
import com.ithawk.demo.spring.custom.ioc.RuntimeBeanReference;
import com.ithawk.demo.spring.custom.ioc.TypedStringValue;

public class BeanDefinitionValueResolver {
    private BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resolveValue(Object value) throws Exception{
        Object valueToUse = null;
        if (value instanceof TypedStringValue){
            TypedStringValue typedStringValue = (TypedStringValue) value;
            String stringValue = typedStringValue.getValue();
            Class<?> targetType = typedStringValue.getTargetType();

            valueToUse = handleType(stringValue,targetType);
        }else if(value instanceof RuntimeBeanReference){
            RuntimeBeanReference beanReference = (RuntimeBeanReference) value;
            String ref = beanReference.getRef();
            // 此处有可能会发生循环依赖问题
            valueToUse = beanFactory.getBean(ref);
        }
        return valueToUse;
    }

    private Object handleType(String stringValue, Class<?> targetType) {
        if (targetType == Integer.class){
            return Integer.parseInt(stringValue);
        }else if (targetType == String.class){
            return stringValue;
        }// ......
        return null;
    }

}

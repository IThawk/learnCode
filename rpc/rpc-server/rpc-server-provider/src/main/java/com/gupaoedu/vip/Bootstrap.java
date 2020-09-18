package com.gupaoedu.vip;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class Bootstrap
{
    public static void main( String[] args )
    {
        ApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
        ((AnnotationConfigApplicationContext) context).start();
    }
}

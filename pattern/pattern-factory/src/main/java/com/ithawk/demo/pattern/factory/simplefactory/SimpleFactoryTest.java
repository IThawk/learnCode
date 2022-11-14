package com.ithawk.demo.pattern.factory.simplefactory;

import com.ithawk.demo.pattern.factory.ICourse;
import com.ithawk.demo.pattern.factory.JavaCourse;
import com.ithawk.demo.pattern.factory.factorymethod.ICourseFactory;

/**
 * 小作坊式的工厂模型
 *
 */
public class SimpleFactoryTest {

    public static void main(String[] args) {

//        ICourse course = new JavaCourse();
//        course.record();


        CourseFactory factory = new CourseFactory();
        ICourse course = factory.create(JavaCourse.class);
        course.record();

    }
}

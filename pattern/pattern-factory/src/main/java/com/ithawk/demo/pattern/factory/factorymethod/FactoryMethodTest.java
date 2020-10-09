package com.ithawk.demo.pattern.factory.factorymethod;

import com.ithawk.demo.pattern.factory.ICourse;

/**
 *
 */
public class FactoryMethodTest {

    public static void main(String[] args) {

        ICourseFactory factory = new PythonCourseFactory();
        ICourse course = factory.create();
        course.record();

        factory = new JavaCourseFactory();
        course = factory.create();
        course.record();

    }

}

package com.ithawk.demo.pattern.factory.factorymethod;

import com.ithawk.demo.pattern.factory.ICourse;
import com.ithawk.demo.pattern.factory.JavaCourse;

/**
 */
public class JavaCourseFactory implements ICourseFactory {
    public ICourse create() {
        return new JavaCourse();
    }
}

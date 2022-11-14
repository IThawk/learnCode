package com.ithawk.demo.pattern.factory.factorymethod;

import com.ithawk.demo.pattern.factory.ICourse;
import com.ithawk.demo.pattern.factory.PythonCourse;

/**
 */
public class PythonCourseFactory implements ICourseFactory {

    public ICourse create() {
        return new PythonCourse();
    }
}

package com.ithawk.demo.pattern.factory.simplefactory;


import com.ithawk.demo.pattern.factory.ICourse;

/**
 *
 */
public class CourseFactory {

//    public ICourse create(String name){
//        if("java".equals(name)){
//            return new JavaCourse();
//        }else if("python".equals(name)){
//            return new PythonCourse();
//        }else {
//            return null;
//        }
//    }

//    public ICourse create(String className){
//        try {
//            if (!(null == className || "".equals(className))) {
//                return (ICourse) Class.forName(className).newInstance();
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    public ICourse create(Class<? extends ICourse> clazz){
        try {
            if (null != clazz) {
                return clazz.newInstance();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}

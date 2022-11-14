package com.ithawk.demo.springboot.ex.dubbo.mock;

import com.ithawk.demo.springboot.ex.dubbo.bean.Student;
import com.ithawk.demo.springboot.ex.dubbo.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentServiceMock implements StudentService {
    @Override
    public void saveStudent(Student student) throws Exception {
        log.info("调用 mock");
    }

    @Override
    public Student findStudentById(int id) {
        log.info("调用 mock");
        return null;
    }

    @Override
    public Integer findStudentsCount() {
        log.info("调用 mock");
        return null;
    }
}

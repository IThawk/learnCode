package demo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.demo.service.impl.UserServiceImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
// 添加配置文件的地址
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class DaoSupportTest {
    @Autowired
	UserServiceImpl userService;

    @Test
    public void EmployeeDaoSupportTest() {
        System.out.println(userService.userTest());
    }

}

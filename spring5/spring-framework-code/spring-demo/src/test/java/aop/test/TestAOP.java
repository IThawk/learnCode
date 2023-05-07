package aop.test;

import aop.service.User1ServiceImpl;
import aop.service.User2ServiceImpl;
import aop.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-aop.xml")
public class TestAOP {

	@Autowired
	private UserService userService;
	@Autowired
	private User1ServiceImpl userService1;

	@Autowired
	@Qualifier("userService2")
//	private UserService userService2;
	private User2ServiceImpl userService2;
	
	@Test
	public void test() {
		userService.saveUser();
		System.out.println("=================");
		userService.saveUser("lisi");
		System.out.println("=================");
		userService1.saveUser("----");
		System.out.println("=================");
		userService.updateUser();

		System.out.println("=================");
		userService2.saveUser("aaaaaa");

		System.out.println("=================");
		userService2.saveUser1();

	}
}

//package ioc.test;
//
//import aop.target.BarCat;
//import aop.target.Foo;
//import org.junit.Test;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//public class TestIoCAnno1 {
//
//	@Test
//	public void test() {
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("aop.target");
//		Foo bar = context.getBean(Foo.class);
//		bar.bar();
//		System.out.println(bar);
//
//		BarCat.BarCat11 bar1 = context.getBean(BarCat.BarCat11.class);
//		System.out.println("。。。。。"+bar1);
//		context.close();
//	}
//
//}

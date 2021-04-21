# SpringBoot

## SpringBoot中CommandLineRunner的作用
    平常开发中有可能需要实现在项目启动后执行的功能，
    SpringBoot提供的一种简单的实现方案就是添加一个model并实现CommandLineRunner接口，实现功能的代码放在实现的run方法中
### 如果有多个类实现CommandLineRunner接口，如何保证顺序
    SpringBoot在项目启动后会遍历所有实现CommandLineRunner的实体类并执行run方法，
     如果需要按照一定的顺序去执行，那么就需要在实体类上使用一个@Order注解（或者实现Order接口）来表明顺序@Order 注解的执行优先级是按value值从小到大顺序。
     CommandLineRunner和ApplicationRunner的执行顺序：
     在spring boot程序中，我们可以使用不止一个实现CommandLineRunner和ApplicationRunner的bean。
     为了有序执行这些bean的run()方法，可以使用@Order注解或Ordered接口。
     例子中我们创建了两个实现CommandLineRunner接口的bean和两个实现ApplicationRunner接口的bean。
     我们使用@Order注解按顺序执行这四个bean。
     如果是spring容器加载成功之后就需要实现 implements   ApplicationListener<ContextRefreshedEvent>

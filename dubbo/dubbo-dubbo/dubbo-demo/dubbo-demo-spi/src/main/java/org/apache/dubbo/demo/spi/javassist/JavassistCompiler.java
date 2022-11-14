package org.apache.dubbo.demo.spi.javassist;

import javassist.*;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Modifier;

/**
 * <p>
 * Java 字节码以二进制的形式存储在 .class 文件中，每一个 .class 文件包含一个 Java 类或接口。
 * Javaassist 就是一个用来 处理 Java 字节码的类库。它可以在一个已经编译好的类中添加新的方法，
 * 或者是修改已有的方法，并且不需要对字节码方面有深入的了解。同时也可以去生成一个新的类对象，通过完全手动的方式。
 * </p>
 * <p>
 * 在 Javassist 中，类 Javaassit.CtClass 表示 class 文件。一个 GtClass (编译时类）对象可以处理一个 class 文件，ClassPool是 CtClass 对象的容器。它按需读取类文件来构造 CtClass 对象，并且保存 CtClass 对象以便以后使用。
 * <p>
 * 需要注意的是 ClassPool 会在内存中维护所有被它创建过的 CtClass，当 CtClass 数量过多时，会占用大量的内存，API中给出的解决方案是 有意识的调用CtClass的detach()方法以释放内存。
 * <p>
 * ClassPool需要关注的方法：
 * <p>
 * getDefault : 返回默认的ClassPool 是单例模式的，一般通过该方法创建我们的ClassPool；
 * appendClassPath, insertClassPath : 将一个ClassPath加到类搜索路径的末尾位置 或 插入到起始位置。通常通过该方法写入额外的类搜索路径，以解决多个类加载器环境中找不到类的尴尬；
 * toClass : 将修改后的CtClass加载至当前线程的上下文类加载器中，CtClass的toClass方法是通过调用本方法实现。需要注意的是一旦调用该方法，则无法继续修改已经被加载的class；
 * get , getCtClass : 根据类路径名获取该类的CtClass对象，用于后续的编辑。
 * CtClass需要关注的方法：
 * <p>
 * freeze : 冻结一个类，使其不可修改；
 * isFrozen : 判断一个类是否已被冻结；
 * prune : 删除类不必要的属性，以减少内存占用。调用该方法后，许多方法无法将无法正常使用，慎用；
 * defrost : 解冻一个类，使其可以被修改。如果事先知道一个类会被defrost， 则禁止调用 prune 方法；
 * detach : 将该class从ClassPool中删除；
 * writeFile : 根据CtClass生成 .class 文件；
 * toClass : 通过类加载器加载该CtClass。
 * 上面我们创建一个新的方法使用了CtMethod类。CtMthod代表类中的某个方法，可以通过CtClass提供的API获取或者CtNewMethod新建，通过CtMethod对象可以实现对方法的修改。
 * <p>
 * CtMethod中的一些重要方法：
 * <p>
 * insertBefore : 在方法的起始位置插入代码；
 * insterAfter : 在方法的所有 return 语句前插入代码以确保语句能够被执行，除非遇到exception；
 * insertAt : 在指定的位置插入代码；
 * setBody : 将方法的内容设置为要写入的代码，当方法被 abstract修饰时，该修饰符被移除；
 * make : 创建一个新的方法。
 * 注意到在上面代码中的：setBody()的时候我们使用了一些符号：
 * <p>
 * Copy
 * // $0=this / $1,$2,$3... 代表方法参数
 * cons.setBody("{$0.name = $1;}");
 * 具体还有很多的符号可以使用，但是不同符号在不同的场景下会有不同的含义，所以在这里就不在赘述，
 * 可以看javassist 的说明文档。http://www.javassist.org/tutorial/tutorial2.html
 * </p>
 */
public class JavassistCompiler {
    public static void main(String[] args) throws Exception {
        // 获取CtClass的工具类   Class Type Class，字节码类型的class
        ClassPool pool = ClassPool.getDefault();
        // 生成一个.class文件
        CtClass ctClass = genericClass(pool);
        // 创建相应的实例，调用其业务方法
        invokeInstance(ctClass);
    }

    private static CtClass genericClass(ClassPool pool) throws Exception {
        // 通过classPool生成一个public的com.abc.Person类的字节码
        CtClass ctClass = pool.makeClass("com.abc.Person");
        // 添加private String name;属性
        CtField nameField = new CtField(pool.getCtClass("java.lang.String"), "name", ctClass);
        nameField.setModifiers(Modifier.PRIVATE);
        ctClass.addField(nameField);

        // 添加private int age;属性
        CtField ageField = new CtField(pool.getCtClass("java.lang.Integer"), "age", ctClass);
        ageField.setModifiers(Modifier.PRIVATE);
        ctClass.addField(ageField);

        // 添加setter getter
        ctClass.addMethod(CtNewMethod.getter("getName", nameField));
        ctClass.addMethod(CtNewMethod.setter("setName", nameField));
        ctClass.addMethod(CtNewMethod.getter("getAge", ageField));
        ctClass.addMethod(CtNewMethod.setter("setAge", ageField));

        // 添加无参构造器
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
        // 这个地方 之间用23会报错？
        String body = "{\nname=\"zhangsan\";\nage=new Integer(23);\n}";
        ctConstructor.setBody(body);
        ctClass.addConstructor(ctConstructor);


        // 添加有参构造   // $0=this / $1,$2,$3... 代表方法参数
        CtConstructor ctConstructor1 = new CtConstructor(new CtClass[]{pool.get("java.lang.String"),
                pool.get("java.lang.Integer")}, ctClass);
        StringBuffer ctConstructorString = new StringBuffer();
        ctConstructorString.append("{\nSystem.out.println(\"name=\"+name);\n");
        ctConstructorString.append("System.out.println(\"age=\"+age);\n");
        ctConstructorString.append("System.out.println(\"-----Person-----\");\n");
        ctConstructorString.append("$0.age=$2;\n");
        ctConstructorString.append("$0.name=$1;\n}");
        ctConstructor1.setBody(ctConstructorString.toString());
        ctClass.addConstructor(ctConstructor1);

        // 添加业务方法
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "personInfo", new CtClass[]{}, ctClass);
        ctMethod.setModifiers(Modifier.PUBLIC);
        StringBuffer buffer = new StringBuffer();
        buffer.append("{\nSystem.out.println(\"name=\"+this.name);\n");
        buffer.append("System.out.println(\"age=\"+this.age);\n}");
        ctMethod.setBody(buffer.toString());
        ctClass.addMethod(ctMethod);


        // 添加业务方法
        StringBuffer method = new StringBuffer();
        method.append("public String getInfo(String name)\n");
        method.append("{\nSystem.out.println(\"name=\"+name);\n");
        method.append("System.out.println(\"age=\"+age);\n");
        method.append("System.out.println(\"-----getInfo-----\");\n");
        method.append("return name;\n}");
        ctClass.addMethod(CtNewMethod.make(method.toString(), ctClass));

        // 把生成的字节码写入到指定的文件
        byte[] bytes = ctClass.toBytecode();
        File file = new File("d:/Person.class");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();

        return ctClass;
    }

    private static void invokeInstance(CtClass ctClass) throws Exception {
        // 获取到ctClass中的Class
        Class<?> clazz = ctClass.toClass();
        // 创建class的实例
        Object instance = clazz.newInstance();

        // 调用业务方法
        instance.getClass()
                .getMethod("getInfo", new Class[]{String.class})
                .invoke(instance, new Object[]{"sss"});

        instance.getClass()
                .getMethod("personInfo", new Class[]{})
                .invoke(instance, new Object[]{});

        System.out.println("使用有参构造函数-------");
        Object instance2 = clazz.getConstructor(new Class[]{String.class, Integer.class})
                .newInstance(new Object[]{"mytest", 13});


        instance2.getClass()
                .getMethod("personInfo", new Class[]{})
                .invoke(instance2, new Object[]{});
        // 调用业务方法
        instance2.getClass()
                .getMethod("getInfo", new Class[]{String.class})
                .invoke(instance2, new Object[]{"sss"});
    }
}



/**
 * @author IThawk
 * @className TestJVM
 * @description: <p>
 *     jmap -dump:format=b,file=heapdump.dump pid  生成堆文件
 * #在tomcat的bin目录下，修改catalina.sh，添加如下的参数
 * JAVA_OPTS="-Dcom.sun.management.jmxremotea
 * -Dcom.sun.management.jmxremote.port=9999
 * -Dcom.sun.management.jmxremote.authenticate=false
 * -Dcom.sun.management.jmxremote.ssl=false"
 * #这几个参数的意思是：
 * #-Dcom.sun.management.jmxremote ：允许使用JMX远程管理
 * #-Dcom.sun.management.jmxremote.port=9999 ：JMX远程连接端口
 * #-Dcom.sun.management.jmxremote.authenticate=false ：不进行身份认证，任何用户都可
 * 以连接
 * #-Dcom.sun.management.jmxremote.ssl=false ：不使用ssl
 * </p>
 * @date 2021/8/5 17:48
 */
public class TestJVM {
    public static void main(String[] args) {
//		String str = System.getProperty("str");
//		if (str == null) {
//			System.out.println("kkb");
//		} else {
//			System.out.println(str);
//		}

        String s = new String("james");
    }

    public void say() {
        System.out.println("class load");
    }
}

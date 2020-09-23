# netty 是一个高性能的成熟的NIO框架
* netty-io： 各类IO编程的比较：
    * bio：同步阻塞io
        * java.net.ServerSocket ：启动一个io，检查端口 
        * Socket client = server.accept(); 阻塞，等待客户端信息
    * nio : 异步阻塞io
    * aio : 异步非阻塞io
* netty-tomcat：基于netty实现一个简易版Tomcat
* netty-rpc：基于netty实现一个简易版远程过程调用例子
       ```
          
          在java的动态代理机制中，有两个重要的类或接口，一个是 InvocationHandler(Interface)、
          另一个则是 Proxy(Class)，这一个类和接口是实现我们动态代理所必须用到的。首先我们先来看看java的API帮助文档是怎么样对这两个类进行描述的
          com.ithawk.netty.demo.rpc.consumer.proxy.RpcProxy
       ```
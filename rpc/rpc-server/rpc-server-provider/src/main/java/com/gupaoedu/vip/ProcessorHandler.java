package com.gupaoedu.vip;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ProcessorHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private Map<String,Object> handlerMap;


    public ProcessorHandler(Map<String,Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        Object result=invoke(rpcRequest);
        channelHandlerContext.writeAndFlush(result).addListener(ChannelFutureListener.CLOSE);

    }

    private Object invoke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //反射调用
        String serviceName=request.getClassName();
        String version=request.getVersion();
        //增加版本号的判断
        if(!StringUtils.isEmpty(version)){
            serviceName+="-"+version;
        }

        Object service=handlerMap.get(serviceName);
        if(service==null){
            throw new RuntimeException("service not found:"+serviceName);
        }
        Object[] args=request.getParameters(); //拿到客户端请求的参数
        Method method=null;
        Class clazz=Class.forName(request.getClassName()); //跟去请求的类进行加载
        method=clazz.getMethod(request.getMethodName(),request.getParamTypes()); //sayHello, saveUser找到这个类中的方法
        Object result=method.invoke(service,args);//HelloServiceImpl 进行反射调用
        return result;
    }
}

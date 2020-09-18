package com.gupaoedu.vip;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */

@RpcService(IPaymentService.class)
public class PaymentServiceImpl implements IPaymentService{
    @Override
    public void doPay() {
        System.out.println("执行doPay方法");
    }
}

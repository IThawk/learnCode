package com.ithawk.demo.spring5.webflux.pps;

import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

/**
 * 订阅者
 */
public class SomeSubscriber implements Flow.Subscriber<String> {
    // 声明订阅令牌
    private Flow.Subscription subscription;

    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(8);
    }
    public void onNext(String item) {
        System.out.println("订阅者正在处理的消息数据为：" + item);
        try {
            TimeUnit.MICROSECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.subscription.request(10);
    }
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        this.subscription.cancel();
    }
    public void onComplete() {
        System.out.println("发布者已关闭，订阅者将所有消息全部处理完成");
    }
}


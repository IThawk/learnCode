package com.ithawk.demo.spring5.webflux.ps;

import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

/**
 * 订阅者
 */
public class SomeSubscriber implements Flow.Subscriber<Integer> {
    // 声明订阅令牌
    private Flow.Subscription subscription;

    // 当订阅关系确立后，触发该方法的执行（只执行一次）
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("=== 执行订阅者的onSubscribe()方法 ===");
        this.subscription = subscription;
        // 请求8条消息
        this.subscription.request(8);
    }

    // 消息在这里进行消费
    public void onNext(Integer item) {
        System.out.println("订阅者正在处理的消息数据为：" + item);
       try {
            TimeUnit.MICROSECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(item < 30) {
            this.subscription.cancel();
        }

        // 每消费一条消息，就再请求10条消息
        this.subscription.request(10);
    }

    // 订阅关系的异常、消费过程的异常等均会触发该方法的执行
    public void onError(Throwable throwable) {
        System.out.println(" --- 执行onError()方法 ---");
        throwable.printStackTrace();
        this.subscription.cancel();
    }

    // publisher的close()方法执行完毕后会触发该方法的执行
    public void onComplete() {
        System.out.println("发布者已关闭，订阅者将所有消息全部处理完成");
    }
}


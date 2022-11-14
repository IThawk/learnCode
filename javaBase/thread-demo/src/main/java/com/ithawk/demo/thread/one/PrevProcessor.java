package com.ithawk.demo.thread.one;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 前置处理流程
 * LinkedBlockingQueue
 */
public class PrevProcessor extends Thread implements IRequestProcessor {

    //阻塞队列
    LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<>();

    private IRequestProcessor nextProcessor;

    private volatile boolean isFinish = false;

    public PrevProcessor() {
    }

    public PrevProcessor(IRequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    //对外提供关闭的方法
    public void shutdown() {
        isFinish = true;
    }

    @Override
    public void run() {
        while (!isFinish) { //不建议这么写
            try {
                //阻塞式获取数据
                Request request = requests.take();
                //真正的处理逻辑
                System.out.println("prevProcessor:" + request);
                //交给下一个责任链
                nextProcessor.process(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void process(Request request) {
        //TODO 根据实际需求去做一些处理
        requests.add(request);
    }
}

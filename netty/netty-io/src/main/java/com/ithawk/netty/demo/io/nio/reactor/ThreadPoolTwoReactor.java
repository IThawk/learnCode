package com.ithawk.netty.demo.io.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author IThawk
 * @className Reactor
 * @description: 单线程版Reactor
 * @date 2022/1/7 10:15
 */
public class ThreadPoolTwoReactor {


    final ServerSocketChannel serverSocketChannel;

    Selector[] selectors; // also create threads
    AtomicInteger next = new AtomicInteger(0);
    ExecutorService sunReactors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws IOException {
        new ThreadPoolTwoReactor(1234);
    }

    public ThreadPoolTwoReactor(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        selectors = new Selector[4];
        for (int i = 0; i < 4; i++) {
            Selector selector = Selector.open();
            selectors[i] = selector;
            SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            key.attach(new Acceptor());
            new Thread(() -> {
                while (!Thread.interrupted()) {
                    try {
                        selector.select();
                        Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        for (SelectionKey selectionKey : selectionKeys) {
                            dispatch(selectionKey);
                        }
                        selectionKeys.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    private void dispatch(SelectionKey selectionKey) {
        Runnable run = (Runnable) selectionKey.attachment();
        if (run != null) {
            run.run();
        }
    }

    class Acceptor implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel connection = serverSocketChannel.accept();
                if (connection != null)
                    sunReactors.execute(new ThreadPoolTwoHandler(selectors[next.getAndIncrement() % selectors.length], connection));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}

class ThreadPoolTwoHandler implements Runnable {

    private final static int DEFAULT_SIZE = 1024;

    private final SocketChannel socketChannel;

    private final SelectionKey seletionKey;

    private static final int READING = 0;

    private static final int SENDING = 1;

    private int state = READING;

    ByteBuffer inputBuffer = ByteBuffer.allocate(DEFAULT_SIZE);

    ByteBuffer outputBuffer = ByteBuffer.allocate(DEFAULT_SIZE);

    private Selector selector;

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
            .availableProcessors());
    private static final int PROCESSING = 3;

    public ThreadPoolTwoHandler(Selector selector, SocketChannel channel) throws IOException {
        this.selector = selector;
        this.socketChannel = channel;
        socketChannel.configureBlocking(false);
        this.seletionKey = socketChannel.register(selector, 0);
        seletionKey.attach(this);
        seletionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        if (state == READING) {
            read();
        } else if (state == SENDING) {
            write();
        }

    }

    class Sender implements Runnable {

        @Override
        public void run() {
            try {
                socketChannel.write(outputBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (outIsComplete()) {
                seletionKey.cancel();
            }
        }

    }

    private void write() {
        try {
            socketChannel.write(outputBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (outIsComplete()) {
            seletionKey.cancel();
        }

    }

    private void read() {
        try {
            socketChannel.read(inputBuffer);
            if (inputIsComplete()) {
                process();
                executorService.execute(new Processer());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public boolean inputIsComplete() {
        return true;
    }

    public boolean outIsComplete() {
        return true;

    }

    public void process() {

    }

    synchronized void processAndHandOff() {
        process();
        state = SENDING; // or rebind attachment
        seletionKey.interestOps(SelectionKey.OP_WRITE);
        selector.wakeup();
    }

    class Processer implements Runnable {
        public void run() {
            processAndHandOff();
        }
    }

}
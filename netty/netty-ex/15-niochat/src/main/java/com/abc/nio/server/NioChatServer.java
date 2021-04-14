package com.abc.nio.server;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class NioChatServer {
    // 开启Server的群聊支持功能
    public void enableChat(ServerSocketChannel serverChannel, Selector selector) throws Exception {
        System.out.println("chatServer启动。。。");
        while (true) {
            if (selector.select(1000) == 0) {
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            for (SelectionKey key : selectionKeys) {
                // 处理客户端上线
                if (key.isAcceptable()) {
                    SocketChannel clientChannel = serverChannel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                    // 获取到client地址
                    String msg = clientChannel.getRemoteAddress() + "-上线了";
                    // 将上线通知广播给所有在线的其它client
                    sendMSGToOtherClientOnline(selector, clientChannel, msg);
                }

                // 处理客户端发送消息
                if (key.isReadable()) {
                    SocketChannel clientChannel = (SocketChannel)key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    clientChannel.read(buffer);
                    // 获取来自于client的消息，trim()将buffer中没有数据的内容转为的空格去掉
                    String msgFromClient = new String(buffer.array()).trim();
                    if (msgFromClient.length() > 0) {
                        // 获取到client地址
                        SocketAddress clientAddr = clientChannel.getRemoteAddress();
                        // 构成要发送给其它client的消息
                        String msgToSend = clientAddr + " say：" + msgFromClient;
                        // 若client发送的是字符串"88"，则表示其要下线
                        if ("88".equals(msgFromClient)) {
                            msgToSend = clientAddr + "下线";
                            // 取消当前key，即放弃其所对应的channel，
                            // 将其对应的channel从selector中去掉
                            key.cancel();
                        }
                        // 将client消息广播给所有在线的其它client
                        sendMSGToOtherClientOnline(selector, clientChannel, msgToSend);
                    }
                }  // end-if
                selectionKeys.remove(key);
            } // end-for
        }
    }

    private void sendMSGToOtherClientOnline(Selector selector, SocketChannel self, String msg) throws IOException {
        // 遍历所有注册到selector的channel，即所有在线的client
        for (SelectionKey key : selector.keys()) {
            SelectableChannel channel = key.channel();
            // 将消息发送给所有其它client
            if (channel instanceof SocketChannel && channel != self) {
                ((SocketChannel) channel).write(ByteBuffer.wrap(msg.trim().getBytes()));
            }
        }
    }
}

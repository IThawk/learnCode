package com.abc.servnet;

/**
 * 定义Servnet规范
 */
public abstract class Servnet {
    public abstract void doGet(NettyRequest request, NettyResponse response) throws Exception;
    public abstract void doPost(NettyRequest request, NettyResponse response) throws Exception;
}

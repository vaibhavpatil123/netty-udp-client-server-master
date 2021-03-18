package com.netty.example.tcp.server;

import io.netty.channel.ChannelFuture;

public class ServerFlow {
    public static ChannelFuture OpenServerPort(int port) {
        try {
            NettyServerConnection nettyServer = new NettyServerConnection(port);
            nettyServer.connectLoop();
            return nettyServer.getChannelFuture();
        } catch (Exception e) {
        }
        return null;
    }
}
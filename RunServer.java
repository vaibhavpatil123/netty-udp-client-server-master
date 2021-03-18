package com.netty.example.tcp.server;

public class RunServer {
    public static void main(String[] args) throws Exception{
            ServerFlow.OpenServerPort(12003).channel().closeFuture().sync();

    }
}

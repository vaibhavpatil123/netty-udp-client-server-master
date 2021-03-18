    package com.netty.example.tcp.client;

    import com.netty.example.tcp.server.NettyServerHandler;
    import io.netty.bootstrap.Bootstrap;
    import io.netty.channel.*;
    import io.netty.channel.nio.NioEventLoopGroup;
    import io.netty.channel.socket.SocketChannel;
    import io.netty.channel.socket.nio.NioSocketChannel;

    import java.net.SocketAddress;
    import java.util.Map;
    import java.util.concurrent.ConcurrentHashMap;

    public class NettyClientConnection {
        int port;
        public static Map<String ,NettyClientHandler> channelMap=new ConcurrentHashMap<>();
        public static EventLoopGroup workGroup = new NioEventLoopGroup();


        public NettyClientConnection(int port){
            this.port = port;
        }

        public NettyClientHandler getChannel(String key) throws Exception {
            if(channelMap.containsKey(key)){
                return channelMap.get(key);
            }
            try{
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(workGroup);
                bootstrap.channel(NioSocketChannel.class);
                bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
                bootstrap.option(ChannelOption.AUTO_READ, true);
                bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
                NettyClientHandler handler=    new NettyClientHandler();
                bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                       socketChannel.pipeline().addLast(handler);
                    }


                });
                ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", this.port).sync();

                channelMap.put(key,  handler);
                return handler;
            }finally{
            }
        }
        public void shutdown(){
            workGroup.shutdownGracefully();
        }
    }

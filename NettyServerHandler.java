package com.netty.example.tcp.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettyServerHandler extends SimpleChannelInboundHandler {

  @Override
  public void channelActive(ChannelHandlerContext channelHandlerContext) {
   // channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("Netty Rocks!", CharsetUtil.UTF_8));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
    cause.printStackTrace();
    channelHandlerContext.close();
  }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

     byte[] bytes = new byte[((ByteBuf) msg).readableBytes()];
      ((ByteBuf) msg).readBytes(bytes);
      channelHandlerContext.channel().writeAndFlush(Unpooled.copiedBuffer(" "+new String(bytes), CharsetUtil.UTF_8));
      channelHandlerContext.flush();
    }
}
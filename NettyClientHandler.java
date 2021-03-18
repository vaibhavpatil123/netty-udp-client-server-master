package com.netty.example.tcp.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class NettyClientHandler extends SimpleChannelInboundHandler {
  public String response;
  public ChannelHandlerContext channelHandlerContext;
  public Map<String, String> responseMap = new ConcurrentHashMap<>();
  public String request;

  public ChannelHandlerContext getChannelHandlerContext() {
    return channelHandlerContext;
  }

  public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
    this.channelHandlerContext = channelHandlerContext;
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    System.out.print("channelInactive channelInactive channelInactive");
  }

  @Override
  public void channelActive(ChannelHandlerContext channelHandlerContext) {
    this.channelHandlerContext = channelHandlerContext;
    this.channelHandlerContext = channelHandlerContext;
    System.out.print("channelActive");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
    this.channelHandlerContext = channelHandlerContext;
    cause.printStackTrace();
    channelHandlerContext.close();
  }

  @Override
  protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object msg)
      throws Exception {
    this.channelHandlerContext = channelHandlerContext;
    if (msg != null) {
      byte[] bytes = new byte[((ByteBuf) msg).readableBytes()];
      ((ByteBuf) msg).readBytes(bytes);
      this.response = " RES " + new String(bytes);
      // System.out.print("request response "+request);
      responseMap.put(request, "" + new String(bytes));
    }
  }
}

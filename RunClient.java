package com.netty.example.tcp.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class RunClient {
  public static void main(String[] args) throws Exception {

    try {
      // Client point to server running on port 12003
      NettyClientConnection nettyClient = new NettyClientConnection(12003);
      NettyClientHandler handler = null;
      handler = nettyClient.getChannel("A");
      final NettyClientHandler finalHandler = handler;
      // send request to server and wait till response found
      sendRequestServer(finalHandler, 1);
      sendRequestServer(finalHandler, 2);
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }
  }

  private static void sendRequestServer(NettyClientHandler finalHandler, int count) {
    new Thread() {
      @Override
      public void run() {
        try {
          finalHandler.request = count + "";
          finalHandler
              .channelHandlerContext
              .channel()
              .writeAndFlush(Unpooled.copiedBuffer("\n \n Req " + count, CharsetUtil.UTF_8))
              .sync()
              .addListener(
                  new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                      if (future.isSuccess()) {
                        future.channel().pipeline().read();
                        System.out.print("Send request complete isSuccess!!! ");

                      } else {
                        System.out.print("Send request complete not Success");
                        future.channel().close();
                      }
                    }
                  });
          // wait till your transaction response not get from Netty
          for (int j = 0; j < 20; j++) {
            Thread.sleep(10 + new Random().nextInt(30));
            System.out.print("Getting response in reTry mode count {0} " + j);
            if (finalHandler.responseMap.get(finalHandler.request) != null) {
              System.out.print("Wait finished ");
              break;
            }
          }
          System.out.print(finalHandler.responseMap.get(finalHandler.request));
        } catch (InterruptedException ex) {
          System.err.println(ex.getMessage());
        }
      }
    }.start();
  }
}

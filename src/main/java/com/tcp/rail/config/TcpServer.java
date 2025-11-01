package com.tcp.rail.config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;


@Component
public class TcpServer {

    private final TcpServerHandler tcpServerHandler;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    // You could also inject this from application.yml
    private final int port = 7788;  // 👈 Your TCP port

    public TcpServer(TcpServerHandler tcpServerHandler) {
        this.tcpServerHandler = tcpServerHandler;
    }

    @PostConstruct
    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) {
                     ch.pipeline().addLast(new io.netty.handler.codec.string.StringDecoder());
                     ch.pipeline().addLast(new io.netty.handler.codec.string.StringEncoder());
                     ch.pipeline().addLast(tcpServerHandler);
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)
             .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();  // 👈 TCP port binding happens here
            System.out.println("🚀 TCP Server started on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        System.out.println("🛑 TCP Server stopped.");
    }
}

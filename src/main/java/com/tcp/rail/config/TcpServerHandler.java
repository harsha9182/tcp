package com.tcp.rail.config;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable  // ✅ make it sharable
public class TcpServerHandler extends SimpleChannelInboundHandler<String> {

    private final TcpMessageStore messageStore;

    public TcpServerHandler(TcpMessageStore messageStore) {
        this.messageStore = messageStore;

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("🔌 New connection from {}", ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info("📩 Received: {}", msg);
        messageStore.addMessage(msg); // ✅ store message in memory
        ctx.writeAndFlush("ACK\n"); // reply to device
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("❌ Disconnected: {}", ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("⚠️ TCP Handler Error", cause);
        ctx.close();
    }
}

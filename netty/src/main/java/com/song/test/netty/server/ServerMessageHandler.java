package com.song.test.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerMessageHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] request = new byte[buf.readableBytes()];
        buf.readBytes(request);
        String req = new String(request, "utf-8");
        System.out.println(req);
        super.channelRead(ctx, msg);
    }
}

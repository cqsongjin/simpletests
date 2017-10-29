package com.song.test.netty.client;

import com.song.test.netty.model.OFProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientMessageHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        OFProtocol protocol = new OFProtocol();
        protocol.setVersion((short) 2);
        protocol.setType((short) 3);
        protocol.setLength(100);
        for (int i = 0; i < 100; i++) {
            ctx.channel().writeAndFlush(protocol);
            System.out.println("write message");
        }
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ctx.fireChannelRead(msg);
    }
}

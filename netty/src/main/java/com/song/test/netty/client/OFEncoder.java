package com.song.test.netty.client;

import com.song.test.netty.model.OFProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class OFEncoder extends MessageToByteEncoder<OFProtocol>{

    @Override
    protected void encode(ChannelHandlerContext ctx, OFProtocol msg, ByteBuf out) throws Exception {
        System.out.println("encode");
        out.writeShort(msg.getVersion());
        out.writeShort(msg.getType());
        out.writeInt(msg.getLength());
        out.writeInt(msg.getXid());
        out.writeInt(msg.getMpDeviceType());
        out.writeInt(msg.getMpDeviceId());
        out.writeBytes("\n".getBytes());
    }
}

package com.song.test.netty.server;

import com.song.test.netty.model.OFProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class OFDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        OFProtocol protocol = new OFProtocol();
        protocol.setVersion((short) in.readUnsignedShort());
        protocol.setType((short) in.readUnsignedShort());
        protocol.setLength((int) in.readUnsignedInt());
        System.out.println(protocol.getLength());
    }
}

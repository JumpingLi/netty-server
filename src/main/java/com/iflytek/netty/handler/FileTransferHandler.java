package com.iflytek.netty.handler;

import com.iflytek.netty.rpc.entity.FileTransferDO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author: JiangPing Li
 * @date: 2018-08-06 15:36
 */
@Slf4j
@Component
@Sharable
public class FileTransferHandler extends ChannelInboundHandlerAdapter {
    private int byteRead;
    private volatile int start = 0;
    private String file_dir = "D:\\file_target";


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FileTransferDO) {
            FileTransferDO ef = (FileTransferDO) msg;
            byte[] bytes = ef.getBytes();
            byteRead = ef.getEndPos();
            String md5 = ef.getFileName();
            String path = file_dir + File.separator + md5;
            File file = new File(path);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(start);
            randomAccessFile.write(bytes);
            start = start + byteRead;
            if (byteRead > 0) {
                ctx.writeAndFlush(start);
            } else {
                randomAccessFile.close();
//                ctx.close();
            }
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);//.addListener(ChannelFutureListener.CLOSE);
    }
}

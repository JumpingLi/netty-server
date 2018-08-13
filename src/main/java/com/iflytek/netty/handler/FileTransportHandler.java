package com.iflytek.netty.handler;

import com.iflytek.netty.rpc.entity.TransportRequest;
import com.iflytek.netty.rpc.entity.TransportResponse;
import com.iflytek.netty.rpc.util.GzipUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author: JiangPing Li
 * @date: 2018-08-07 11:14
 */
@Slf4j
@Component
@Sharable
public class FileTransportHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        TransportResponse resp = new TransportResponse();
        resp.setId("500");
        resp.setName("服务端发生异常");
        resp.setMessage("服务端发生异常");
        ctx.writeAndFlush(resp);
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //ctx.write(Object)方法不会使消息写入到通道上，他被缓冲在了内部，
        // 你需要调用ctx.flush()方法来把缓冲区中数据强行输出
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
        // 读完数据即关闭连接
//                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TransportRequest req = (TransportRequest) msg;
        log.debug("---Server Received: " + req.getId() + ", " + req.getName() + ", " + req.getMessage());
        // 进行文件资源的还原
        byte[] attachment = GzipUtils.ungzip(req.getAttachment());

        // 获取保存目录
        String path = System.getProperty("user.dir") + File.separatorChar + "receive" + File.separatorChar + req.getName();

        log.debug("---文件保存路径：" + path);
        // 进行文件的保存
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(attachment);
        fos.close();

        // 传回给客户端的响应数据
        TransportResponse resp = new TransportResponse();
        resp.setId("文件大小:" + req.getId());
        resp.setName("传输文件名:" + req.getName());
        resp.setMessage("响应消息: " + req.getMessage());
        ctx.writeAndFlush(resp)
                .addListener(ChannelFutureListener.CLOSE);//关闭连接

//        ByteBuf in = (ByteBuf) msg;
//        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
//        ctx.write(in);
    }
}

package com.iflytek.netty.handler;

import com.iflytek.netty.common.MethodInvokeMeta;
import com.iflytek.netty.dispatcher.RequestDispatcher;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: JiangPing Li
 * @date: 2018-08-06 9:31
 */
@Slf4j
@Component
@Sharable
public class ServerChannelHandler extends ChannelHandlerAdapter {

    @Resource
    private RequestDispatcher dispatcher;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MethodInvokeMeta invokeMeta = (MethodInvokeMeta) msg;
        // 屏蔽toString()方法
//        if (!invokeMeta.getMethodName().endsWith("toString()")
//                && !"class java.lang.String".equals(invokeMeta.getReturnType().toString())){
//            log.info("客户端传入参数: {}, 参数类类型: {},返回值类型: {},",
//                    invokeMeta.getArgs(),invokeMeta.getParameterTypes(), invokeMeta.getReturnType());
//        }
        dispatcher.dispatcher(ctx, invokeMeta);
    }
}

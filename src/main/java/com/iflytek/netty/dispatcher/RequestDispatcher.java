package com.iflytek.netty.dispatcher;

import com.iflytek.netty.common.*;
import com.iflytek.netty.config.NettyConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: JiangPing Li
 * @date: 2018-08-06 9:35
 */
@Component
public class RequestDispatcher implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 发送
     * @param ctx
     * @param invokeMeta
     */
    public void dispatcher(final ChannelHandlerContext ctx, final MethodInvokeMeta invokeMeta) {
        ChannelFuture f = null;
        try {
            Class<?> interfaceClass = invokeMeta.getInterfaceClass();
            String name = invokeMeta.getMethodName();
            Object[] args = invokeMeta.getArgs();
            Class<?>[] parameterTypes = invokeMeta.getParameterTypes();
            Object targetObject = applicationContext.getBean(interfaceClass);
            Method method = targetObject.getClass().getMethod(name, parameterTypes);
            Object obj = method.invoke(targetObject, args);
            if (obj == null) {
                f = ctx.writeAndFlush(NullWritable.nullWritable());
            } else {
                f = ctx.writeAndFlush(obj);
            }
            f.addListener(ChannelFutureListener.CLOSE);
        } catch (Exception e) {
            f = ctx.writeAndFlush(e.getMessage());
        } finally {
            if (f != null) {
                f.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
}

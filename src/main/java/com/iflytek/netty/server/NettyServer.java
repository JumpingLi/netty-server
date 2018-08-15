package com.iflytek.netty.server;

import com.iflytek.netty.config.NettyConfig;
import com.iflytek.netty.handler.FileTransferHandler;
import com.iflytek.netty.handler.FileTransportHandler;
import com.iflytek.netty.handler.ServerChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;


/**
 * @author: JiangPing Li
 * @date: 2018-08-06 9:13
 */
@Slf4j
@Component
public class NettyServer {
    /**
     * 启动NIO服务的辅助启动类
     */
    private ServerBootstrap serverBootstrap = new ServerBootstrap();
    /**
     * BOSS 连接处理 用来处理I/O操作的多线程事件循环器
     */
    private EventLoopGroup boss = new NioEventLoopGroup();
    /**
     * Worker 事件处理 用来处理I/O操作的多线程事件循环器
     */
    private EventLoopGroup work = new NioEventLoopGroup();

    /**
     * 文件传输 处理器
     */
    @Autowired
    private FileTransportHandler fileTransportHandler;

    @Autowired
    private FileTransferHandler fileTransferHandler;
    /**
     * NETT服务器配置类
     */
    @Resource
    private NettyConfig nettyConfig;

    /**
     * rpc 调用 处理器
     */
    @Autowired
    private ServerChannelHandler serverChannelHandler;

    private static final boolean SSL = System.getProperty("ssl") != null;
    /**
     * 关闭服务器方法
     */
    @PreDestroy
    public void close() {
        log.info("关闭netty服务器....");
        //优雅退出
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }

    /**
     * 开启 服务线程
     */
    public void start() {
        int port = nettyConfig.getPort();
        final SslContext sslCtx;
        try {
            if (SSL) {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
            } else {
                sslCtx = null;
            }
            serverBootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
//                    .option(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            if (sslCtx != null) {
                                pipeline.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.weakCachingConcurrentResolver(null)));
                            pipeline.addLast(serverChannelHandler);
                        }
                    });

            log.debug("netty服务器在[{}]端口启动监听....", port);
            ChannelFuture f = serverBootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.info("netty服务器启动异常: ", e);
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}

package com.iflytek.netty;

import com.iflytek.netty.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author jpli3
 */
@SpringBootApplication(exclude = {CodecsAutoConfiguration.class})
@MapperScan(basePackages = {"com.iflytek.netty.dao.mapper"})
public class NettyServerApplication implements CommandLineRunner {

    @Autowired
    private NettyServer server;

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        server.start();
    }
}

package com.iflytek.netty.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

/**
 * @author: JiangPing Li
 * @date: 2018-08-16 11:59
 */
@Configuration
public class MapperNoWarn extends MybatisAutoConfiguration {

    public MapperNoWarn(org.mybatis.spring.boot.autoconfigure.MybatisProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer>> configurationCustomizersProvider) {
        super(properties, interceptorsProvider, resourceLoader, databaseIdProvider, configurationCustomizersProvider);
    }
}

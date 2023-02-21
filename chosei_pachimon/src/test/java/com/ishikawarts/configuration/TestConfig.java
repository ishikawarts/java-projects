package com.ishikawarts.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.ishikawarts" })
@MapperScan({ "com.ishikawarts.dao" })
public class TestConfig {

}

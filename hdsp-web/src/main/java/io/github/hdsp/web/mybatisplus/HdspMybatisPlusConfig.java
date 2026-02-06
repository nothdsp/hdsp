package io.github.hdsp.web.mybatisplus;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class HdspMybatisPlusConfig {
    
    @PostConstruct
    public void configMybatisPlus() {
        System.setProperty("mybatis-plus.global-config.banner", "false");
        System.setProperty("mybatis-plus.global-config.db-config.enable-sql-runner", "true");
    }
}

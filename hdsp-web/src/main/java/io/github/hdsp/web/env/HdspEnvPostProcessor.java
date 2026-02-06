package io.github.hdsp.web.env;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

/**
 * EnvironmentPostProcessor 的执行时机：
 * 在 SpringApplication 创建 ConfigurableEnvironment 之后；
 * 在加载 application.properties / application.yml 之前或同时（取决于实现）；
 * 远早于 @Configuration、@Component、CommandLineRunner 等 Bean 的初始化；
 * 早于 Spring Boot 自动配置（AutoConfiguration）的处理。
 */
public class HdspEnvPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> props = new HashMap<>();
        props.put("jasypt.encryptor.password", "AiB5j9610k7MPIaFVytJC834EwGcp9gh");
        environment.getPropertySources().addFirst(
                new MapPropertySource("hdspJasyptProperties", props));
    }

}
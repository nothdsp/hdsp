package io.github.hdsp.jasypt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

public class HdspJasyptEnvPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Map<String, Object> props = new HashMap<>();
        props.put("jasypt.encryptor.password", "AiB5j9610k7MPIaFVytJC834EwGcp9gh");
        environment.getPropertySources().addFirst(
                new MapPropertySource("hdspJasyptProperties", props)
            );
    }

}

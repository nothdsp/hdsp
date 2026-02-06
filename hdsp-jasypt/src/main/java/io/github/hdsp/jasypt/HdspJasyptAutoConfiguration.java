
package io.github.hdsp.jasypt;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

@AutoConfiguration
@EnableEncryptableProperties
public class HdspJasyptAutoConfiguration {

    private static final String DEFAULT_PASSWORD = "AiB5j9610k7MPIaFVytJC834EwGcp9gh";

    public HdspJasyptAutoConfiguration(ConfigurableEnvironment environment) {
        configureJasyptPassword(environment);
    }

    private void configureJasyptPassword(ConfigurableEnvironment environment) {
        Map<String, Object> jasyptProperties = new HashMap<>();
        jasyptProperties.put("jasypt.encryptor.password", DEFAULT_PASSWORD);
        
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new MapPropertySource("hdspJasyptProperties", jasyptProperties));
    }
}

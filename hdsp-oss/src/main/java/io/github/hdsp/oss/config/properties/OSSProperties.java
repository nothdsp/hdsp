package io.github.hdsp.oss.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * OSS 配置属性
 */
@Data
@ConfigurationProperties(prefix = "hdsp.oss")
public class OSSProperties {
    /**
     * OSS 存储桶名称
     */
    private String bucket;
    /**
     * OSS 服务端地址
     */
    private String endpoint;
    /**
     * OSS 访问密钥 ID
     */
    private String accessKeyId;
    /**
     * OSS 访问密钥 Secret
     */
    private String accessKeySecret;
}

package io.github.hdsp.oss.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import io.github.hdsp.oss.config.properties.OSSProperties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

/**
 * OSS 配置类
 */
@AutoConfiguration
@EnableConfigurationProperties(OSSProperties.class)
public class OSSConfiguration {

    @Autowired
    private OSSProperties ossProperties;

    /**
     * 创建 S3 客户端
     * 
     * @return S3 客户端
     */
    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(ossProperties.getEndpoint()))
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(
                                        ossProperties.getAccessKeyId(),
                                        ossProperties.getAccessKeySecret())))
                .forcePathStyle(true)
                .build();
    }

    /**
     * 创建 S3 预签名器
     * 
     * @return S3 预签名器
     */
    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .endpointOverride(URI.create(ossProperties.getEndpoint()))
                .region(Region.US_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(
                                        ossProperties.getAccessKeyId(),
                                        ossProperties.getAccessKeySecret())))
                .build();
    }
}

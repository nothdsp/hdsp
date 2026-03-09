package io.github.hdsp.oss.utils;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.extra.spring.SpringUtil;
import io.github.hdsp.oss.config.properties.OSSProperties;
import io.github.hdsp.oss.domain.dto.ObjectDTO;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketAlreadyExistsException;
import software.amazon.awssdk.services.s3.model.BucketAlreadyOwnedByYouException;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

/**
 * OSS 工具类
 */
@Slf4j
public class OSSUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final S3Client s3Client = SpringUtil.getBean(S3Client.class);
    private static final S3Presigner s3Presigner = SpringUtil.getBean(S3Presigner.class);
    private static final OSSProperties ossProperties = SpringUtil.getBean(OSSProperties.class);

    /**
     * 生成预签名上传URL
     * 
     * @param bucket      桶名称
     * @param objectKey   对象键
     * @param contentType 对象内容类型
     * @return 预签名上传URL
     */
    public static String generatePresignedPutUrl(String bucket, String objectKey, String contentType) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
        URL url = presignedRequest.url();
        log.info("Generated presigned upload URL for object: {} in bucket: {}, URL: {}", objectKey, bucket, url);
        return url.toString();
    }

    /**
     * 生成预签名上传URL（默认桶）
     * 
     * @param objectKey   对象键
     * @param contentType 对象内容类型
     * @return 预签名上传URL
     */
    public static String generatePresignedPutUrl(String objectKey, String contentType) {
        return generatePresignedPutUrl(ossProperties.getBucket(), objectKey, contentType);
    }

    /**
     * 上传对象到OSS桶
     * 
     * @param bucket    桶名称
     * @param objectKey 对象键
     * @param object    要上传的对象
     * @return 对象DTO，包含对象名、对象大小、对象扩展名和对象URL
     */
    public static ObjectDTO uploadObject(String bucket, String objectKey, MultipartFile object) {
        ObjectDTO objectDTO = new ObjectDTO();
        try {
            // 创建OSS桶
            createBucket(bucket);
            // 设置桶公开读取权限
            setBucketPolicy(bucket);
            // 上传对象到OSS桶
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(objectKey)
                    .contentType(object.getContentType())
                    .build(), RequestBody.fromInputStream(object.getInputStream(), object.getSize()));

            log.info("Uploaded object: {} to bucket: {}", objectKey, bucket);
            // 设置对象DTO
            objectDTO.setObjectName(object.getOriginalFilename());
            objectDTO.setObjectSize(String.valueOf(object.getSize()));
            objectDTO.setObjectExtension(
                    object.getOriginalFilename().substring(object.getOriginalFilename().lastIndexOf(".")));
            objectDTO.setObjectUrl(ossProperties.getEndpoint() + "/" + bucket + "/" + objectKey);
        } catch (Exception e) {
            log.error("Error uploading object: {} to bucket: {}", objectKey, bucket, e);
        }
        return objectDTO;
    }

    /**
     * 上传对象到OSS桶
     * 
     * @param bucket    桶名称
     * @param object    要上传的对象
     * @return 对象DTO，包含对象名、对象大小、对象扩展名和对象URL
     */
    public static ObjectDTO uploadObject(String bucket, MultipartFile object) {
        return uploadObject(bucket, object.getOriginalFilename(), object);
    }

    /**
     * 上传对象到OSS桶（默认桶）
     * 
     * @param object 要上传的对象
     * @return 对象DTO，包含对象名、对象大小、对象扩展名和对象URL
     */
    public static ObjectDTO uploadObject(MultipartFile object) {
        return uploadObject(ossProperties.getBucket(), object.getOriginalFilename(), object);
    }

    /**
     * 创建OSS桶
     * 
     * @param bucket 桶名称
     */
    private static void createBucket(String bucket) {
        try {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucket).build());
            log.info("Bucket created: {}", bucket);
        } catch (BucketAlreadyExistsException | BucketAlreadyOwnedByYouException e) {
            log.info("Bucket already exists: {}", bucket);
        }
    }

    /**
     * 设置S3桶策略
     * 
     * @param bucket 桶名称
     */
    public static void setBucketPolicy(String bucket) {
        try {
            // 构建策略对象
            Statement statement = new Statement();
            statement.setResource("arn:aws:s3:::" + bucket + "/*");
            Policy policy = new Policy();
            policy.setStatement(Arrays.asList(statement));
            String policyJson = objectMapper.writeValueAsString(policy);
            PutBucketPolicyRequest request = PutBucketPolicyRequest.builder()
                    .bucket(bucket)
                    .policy(policyJson)
                    .build();
            s3Client.putBucketPolicy(request);
            log.info("Bucket policy set for: {}, policyJson: {}", bucket, policyJson);
        } catch (JsonProcessingException e) {
            log.error("Error setting bucket policy: {}", e.getMessage());
        }
    }

    private static class Policy {
        private String version = "2012-10-17";
        private List<Statement> statement;

        @JsonProperty("Version")
        public String getVersion() {
            return version;
        }

        @JsonProperty("Statement")
        public List<Statement> getStatement() {
            return statement;
        }

        public void setStatement(List<Statement> statement) {
            this.statement = statement;
        }
    }

    private static class Statement {
        private String effect = "Allow";
        private Principal principal = new Principal();
        private List<String> action = Arrays.asList("s3:GetObject");
        private List<String> resource;

        @JsonProperty("Effect")
        public String getEffect() {
            return effect;
        }

        @JsonProperty("Principal")
        public Principal getPrincipal() {
            return principal;
        }

        @JsonProperty("Action")
        public List<String> getAction() {
            return action;
        }

        @JsonProperty("Resource")
        public List<String> getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = Arrays.asList(resource);
        }
    }

    private static class Principal {
        private List<String> aws = Arrays.asList("*");

        @JsonProperty("AWS")
        public List<String> getAws() {
            return aws;
        }
    }
}

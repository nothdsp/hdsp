package io.github.hdsp.oss.domain.dto;

import lombok.Data;

/**
 * 对象存储DTO
 */
@Data
public class ObjectDTO {
    /**
     * 对象URL
     */
    private String objectUrl;
    /**
     * 对象名称
     */
    private String objectName;
    /**
     * 对象大小
     */
    private String objectSize;
    /**
     * 对象扩展名
     */
    private String objectExtension;
}

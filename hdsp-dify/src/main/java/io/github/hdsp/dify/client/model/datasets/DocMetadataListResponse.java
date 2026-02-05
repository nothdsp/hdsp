package io.github.hdsp.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 文档元数据列表响应
 *
 * 
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocMetadataListResponse {
    /**
     * 文档元数据列表
     */
    private List<DocMetadata> docMetadata;
    /**
     * 是否启用内置字段
     */
    private Boolean builtInFieldEnabled;

    /**
     * 文档元数据
     *
     * 
     * 
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DocMetadata {
        /**
         * 元数据 ID
         */
        private String id;

        /**
         * 元数据类型
         */
        private String type;

        /**
         * 元数据名称
         */
        private String name;

        /**
         * 元数据使用次数
         */
        @JsonProperty("count")
        private Integer useCount;
    }
}

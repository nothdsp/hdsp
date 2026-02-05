package io.github.hdsp.dify.client.model.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VariableResponse {
    /**
     * 每页项目数
     */
    private Integer limit;

    /**
     * 是否有更多项目
     */
    private Boolean hasMore;

    /**
     * 变量列表
     */
    private List<VariableData> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VariableData {

        /**
         * 变量 ID
         */
        private String id;

        /**
         * 变量名称
         */
        private String name;

        /**
         * 变量类型（字符串、数字、布尔等）
         */
        private String valueType;

        /**
         * 变量值
         */
        private String value;

        /**
         * 变量描述
         */
        private String description;

        /**
         * 创建时间戳
         */
        private long created_at;

        /**
         * 最后更新时间戳
         */
        private long updated_at;
    }
}

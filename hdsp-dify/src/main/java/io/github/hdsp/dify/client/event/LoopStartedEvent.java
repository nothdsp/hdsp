package io.github.hdsp.dify.client.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 循环开始执行事件
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoopStartedEvent extends BaseWorkflowEvent {

    /**
     * 详细内容
     */
    @JsonProperty("data")
    private LoopStartedEvent.LoopStartedData data;

    /**
     * 循环开始执行事件数据
     */
    @Data
    @NoArgsConstructor
    public static class LoopStartedData {

        /**
         * workflow 执行 ID
         */
        @JsonProperty("id")
        private String id;

        /**
         * 节点 ID
         */
        @JsonProperty("node_id")
        private String nodeId;

        /**
         * 节点类型
         */
        @JsonProperty("node_type")
        private String nodeType;

        /**
         * 节点名称
         */
        @JsonProperty("title")
        private String title;

        /**
         * 创建时间
         */
        @JsonProperty("created_at")
        private Long createdAt;

        /**
         * 额外信息
         */
        @JsonProperty("extras")
        private Map<String, Object> extras;

        /**
         * 元数据
         */
        @JsonProperty("metadata")
        private Map<String, Object> metadata;

        /**
         * 输入数据
         */
        @JsonProperty("inputs")
        private Map<String, Object> inputs;

        /**
         * 并行执行 ID
         */
        @JsonProperty("parallel_id")
        private String parallelId;

        /**
         * 并行开始节点 ID
         */
        @JsonProperty("parallel_start_node_id")
        private String parallelStartNodeId;
    }
}
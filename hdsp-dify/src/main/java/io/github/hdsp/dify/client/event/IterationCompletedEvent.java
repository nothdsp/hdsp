package io.github.hdsp.dify.client.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 迭代器执行完成事件
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IterationCompletedEvent extends BaseWorkflowEvent {

    /**
     * 详细内容
     */
    @JsonProperty("data")
    private IterationCompletedData data;

    /**
     * 迭代器执行完成事件数据
     */
    @Data
    @NoArgsConstructor
    public static class IterationCompletedData {

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
         * 输出数据
         */
        @JsonProperty("outputs")
        private Map<String, Object> outputs;

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
         * 输入数据
         */
        @JsonProperty("inputs")
        private Map<String, Object> inputs;

        /**
         * 执行状态
         */
        @JsonProperty("status")
        private String status;

        /**
         * 错误信息
         */
        @JsonProperty("error")
        private String error;

        /**
         * 执行耗时（秒）
         */
        @JsonProperty("elapsed_time")
        private Double elapsedTime;

        /**
         * 总token数
         */
        @JsonProperty("total_tokens")
        private Integer totalTokens;

        /**
         * 执行元数据
         */
        @JsonProperty("execution_metadata")
        private Map<String, Object> executionMetadata;

        /**
         * 完成时间
         */
        @JsonProperty("finished_at")
        private Long finishedAt;

        /**
         * 执行步骤数
         */
        @JsonProperty("steps")
        private Integer steps;

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
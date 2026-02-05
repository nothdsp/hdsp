package io.github.hdsp.dify.client.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 迭代器下一次执行事件
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IterationNextEvent extends BaseWorkflowEvent {

    /**
     * 详细内容
     */
    @JsonProperty("data")
    private IterationNextData data;

    /**
     * 迭代器下一次执行事件数据
     */
    @Data
    @NoArgsConstructor
    public static class IterationNextData {

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
         * 当前迭代索引
         */
        @JsonProperty("index")
        private Integer index;

        /**
         * 创建时间
         */
        @JsonProperty("created_at")
        private Long createdAt;

        /**
         * 前一次迭代的输出
         */
        @JsonProperty("pre_iteration_output")
        private Object preIterationOutput;

        /**
         * 额外信息
         */
        @JsonProperty("extras")
        private Map<String, Object> extras;

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

        /**
         * 并行模式运行 ID
         */
        @JsonProperty("parallel_mode_run_id")
        private String parallelModeRunId;

        /**
         * 执行时长（秒）
         */
        @JsonProperty("duration")
        private Double duration;
    }
} 
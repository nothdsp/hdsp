package io.github.hdsp.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 更新知识库请求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDatasetRequest {
    /**
     * 知识库名称
     */
    private String name;

    /**
     * 知识库描述（选填）
     */
    private String description;

    /**
     * 索引模式（选填，建议填写）
     * high_quality 高质量
     * economy 经济
     */
    @JsonProperty("indexing_technique")
    private String indexingTechnique;

    /**
     * 权限（选填，默认 only_me）
     * only_me 仅自己
     * all_team_members 所有团队成员
     * partial_members 部分团队成员
     */
    private String permission;

    /**
     * 嵌入模型提供商（选填）, 必须先在系统内设定好接入的模型，对应的是provider字段
     */
    @JsonProperty("embedding_model_provider")
    private String embeddingModelProvider;

    /**
     * 嵌入模型（选填）
     */
    @JsonProperty("embedding_model")
    private String embeddingModel;

    /**
     * 检索参数（选填，如不填，按照默认方式召回）
     */
    @JsonProperty("retrieval_model")
    private RetrievalModel retrievalModel;

    /**
     * 部分团队成员 ID 列表（选填）
     */
    @JsonProperty("partial_member_list")
    private List<String> partialMemberList;

    /**
     * 外部检索模型（选填）
     */
    @JsonProperty("external_retrieval_model")
    private RetrievalModel externalRetrievalModel;

    /**
     * 外部知识库 ID（选填）
     */
    @JsonProperty("external_knowledge_id")
    private String externalKnowledgeId;

    /**
     * 外部知识库 API_ID（选填）
     */
    @JsonProperty("external_knowledge_api_id")
    private String externalKnowledgeApiId;

    /**
     * 检索模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RetrievalModel {
        /**
         * 检索方法：以下四个关键字之一，必填
         * keyword_search 关键字检索
         * semantic_search 语义检索
         * full_text_search 全文检索
         * hybrid_search 混合检索
         */
        @JsonProperty("search_method")
        private String searchMethod;

        /**
         * 是否启用 Reranking，非必填，如果检索模式为 semantic_search 模式或者 hybrid_search 则传值
         */
        @JsonProperty("reranking_enable")
        private Boolean rerankingEnable;

        /**
         * 重排序模式（字符串）。不传或为 null 则不启用 Rerank。
         * 可选值：weighted_score / reranking_model
         */
        @JsonProperty("reranking_mode")
        private String rerankingMode;

        /**
         * Rerank 模型配置，非必填；当 reranking_mode = reranking_model 时有效
         */
        @JsonProperty("reranking_model")
        private RerankingModel rerankingModel;

        /**
         * 混合检索模式下的权重设置
         */
        private WeightedScoreFields weights;

        /**
         * 返回结果数量，非必填
         */
        @JsonProperty("top_k")
        private Integer topK;

        /**
         * 是否开启 score 阈值
         */
        @JsonProperty("score_threshold_enabled")
        private Boolean scoreThresholdEnabled;

        /**
         * Score 阈值
         */
        @JsonProperty("score_threshold")
        private Double scoreThreshold;
    }

    /**
     * Rerank 模型配置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RerankingModel {
        /**
         * Rerank 模型提供商
         */
        @JsonProperty("reranking_provider_name")
        private String rerankingProviderName;

        /**
         * Rerank 模型名称
         */
        @JsonProperty("reranking_model_name")
        private String rerankingModelName;
    }
} 
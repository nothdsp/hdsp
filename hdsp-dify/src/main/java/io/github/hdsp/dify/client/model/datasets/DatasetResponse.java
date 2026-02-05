package io.github.hdsp.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 知识库响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatasetResponse {
    /**
     * 知识库ID
     */
    private String id;

    /**
     * 知识库名称
     */
    private String name;

    /**
     * 知识库描述
     */
    private String description;

    /**
     * 提供者
     */
    private String provider;

    /**
     * 权限
     */
    private String permission;

    /**
     * 数据源类型
     */
    @JsonProperty("data_source_type")
    private String dataSourceType;

    /**
     * 索引技术
     */
    @JsonProperty("indexing_technique")
    private String indexingTechnique;

    /**
     * 应用数量
     */
    @JsonProperty("app_count")
    private Integer appCount;

    /**
     * 文档数量
     */
    @JsonProperty("document_count")
    private Integer documentCount;

    /**
     * 字数
     */
    @JsonProperty("word_count")
    private Integer wordCount;

    /**
     * 创建者
     */
    @JsonProperty("created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private Long createdAt;

    /**
     * 更新者
     */
    @JsonProperty("updated_by")
    private String updatedBy;

    /**
     * 更新时间
     */
    @JsonProperty("updated_at")
    private Long updatedAt;

    /**
     * Embedding模型
     */
    @JsonProperty("embedding_model")
    private String embeddingModel;

    /**
     * Embedding模型提供商
     */
    @JsonProperty("embedding_model_provider")
    private String embeddingModelProvider;

    /**
     * Embedding是否可用
     */
    @JsonProperty("embedding_available")
    private Boolean embeddingAvailable;

    /**
     * 检索模型字典
     */
    @JsonProperty("retrieval_model_dict")
    private RetrievalModelDict retrievalModelDict;

    /**
     * 标签
     */
    @JsonProperty("tags")
    private List<TagResponse> tags;

    /**
     * 文档格式
     */
    @JsonProperty("doc_form")
    private String docForm;

    /**
     * 外部知识库信息
     */
    @JsonProperty("external_knowledge_info")
    private ExternalKnowledgeInfo externalKnowledgeInfo;

    /**
     * 外部检索模型
     */
    @JsonProperty("external_retrieval_model")
    private ExternalRetrievalModel externalRetrievalModel;

    /**
     * 部分成员列表
     */
    @JsonProperty("partial_member_list")
    private List<String> partialMemberList;

    /**
     * 检索模型字典
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RetrievalModelDict {
        /**
         * 搜索方法
         */
        @JsonProperty("search_method")
        private String searchMethod;

        /**
         * 是否启用重排序
         */
        @JsonProperty("reranking_enable")
        private Boolean rerankingEnable;

        /**
         * 重排序模式
         */
        @JsonProperty("reranking_mode")
        private String rerankingMode;

        /**
         * 重排序模型
         */
        @JsonProperty("reranking_model")
        private RerankingModel rerankingModel;

        /**
         * 混合检索模式下的权重设置
         */
        private WeightedScoreFields weights;

        /**
         * 返回结果数量
         */
        @JsonProperty("top_k")
        private Integer topK;

        /**
         * 是否启用分数阈值
         */
        @JsonProperty("score_threshold_enabled")
        private Boolean scoreThresholdEnabled;

        /**
         * 分数阈值
         */
        @JsonProperty("score_threshold")
        private Double scoreThreshold;
    }

    /**
     * 重排序模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RerankingModel {
        /**
         * 重排序提供商名称
         */
        @JsonProperty("reranking_provider_name")
        private String rerankingProviderName;

        /**
         * 重排序模型名称
         */
        @JsonProperty("reranking_model_name")
        private String rerankingModelName;
    }

    /**
     * 外部知识库信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExternalKnowledgeInfo {
        /**
         * 外部知识库ID
         */
        @JsonProperty("external_knowledge_id")
        private String externalKnowledgeId;

        /**
         * 外部知识库API ID
         */
        @JsonProperty("external_knowledge_api_id")
        private String externalKnowledgeApiId;

        /**
         * 外部知识库API名称
         */
        @JsonProperty("external_knowledge_api_name")
        private String externalKnowledgeApiName;

        /**
         * 外部知识库API端点
         */
        @JsonProperty("external_knowledge_api_endpoint")
        private String externalKnowledgeApiEndpoint;
    }

    /**
     * 外部检索模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExternalRetrievalModel {
        /**
         * 返回结果数量
         */
        @JsonProperty("top_k")
        private Integer topK;

        /**
         * 分数阈值
         */
        @JsonProperty("score_threshold")
        private Double scoreThreshold;

        /**
         * 是否启用分数阈值
         */
        @JsonProperty("score_threshold_enabled")
        private Boolean scoreThresholdEnabled;
    }
}

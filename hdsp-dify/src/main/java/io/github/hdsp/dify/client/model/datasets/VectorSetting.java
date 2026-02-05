package io.github.hdsp.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 向量设置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VectorSetting {
    /**
     * 向量权重
     */
    @JsonProperty("vector_weight")
    private Float vectorWeight;

    /**
     * 嵌入模型名称
     */
    @JsonProperty("embedding_model_name")
    private String embeddingModelName;

    /**
     * 嵌入提供商名称
     */
    @JsonProperty("embedding_provider_name")
    private String embeddingProviderName;
} 
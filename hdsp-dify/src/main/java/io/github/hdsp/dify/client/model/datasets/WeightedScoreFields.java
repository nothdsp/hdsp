package io.github.hdsp.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 混合检索模式下的权重设置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeightedScoreFields {
    /**
     * 权重类型
     */
    @JsonProperty("weight_type")
    private String weightType;

    /**
     * 关键词设置
     */
    @JsonProperty("keyword_setting")
    private KeywordSetting keywordSetting;

    /**
     * 向量设置
     */
    @JsonProperty("vector_setting")
    private VectorSetting vectorSetting;
} 
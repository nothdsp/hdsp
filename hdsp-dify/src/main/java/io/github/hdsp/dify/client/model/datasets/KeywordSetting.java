package io.github.hdsp.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关键词设置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeywordSetting {
    /**
     * 关键词权重
     */
    @JsonProperty("keyword_weight")
    private Float keywordWeight;
} 
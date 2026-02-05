package io.github.hdsp.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 标签绑定请求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagBindRequest {
    /**
     * 标签ID列表，必填
     */
    @JsonProperty("tag_ids")
    private List<String> tagIds;

    /**
     * 知识库ID，必填
     */
    @JsonProperty("target_id")
    private String targetId;
} 
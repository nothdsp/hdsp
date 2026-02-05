package io.github.hdsp.dify.client.model.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 标签列表响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagListResponse {
    /**
     * 标签列表
     */
    private List<TagResponse> data;

    /**
     * 总数
     */
    private Integer total;
} 
package io.github.hdsp.web.domain.vo;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageVO<T> {
    /**
     * 查询结果的元数据表头信息
     */
    private List<Map<String, Object>> meta;
    /**
     * 分页数据
     */
    private List<T> rows;
    /**
     * 总记录数
     */
    private Long total;

}

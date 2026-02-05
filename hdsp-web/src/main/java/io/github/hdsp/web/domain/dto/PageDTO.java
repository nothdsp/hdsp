package io.github.hdsp.web.domain.dto;

import lombok.Data;
 
@Data
public class PageDTO {
 
    /**
     * 当前页码
     */
    private Integer pageNum=1;
 
    /**
     * 每页记录数
     */
    private Integer pageSize=10;

}

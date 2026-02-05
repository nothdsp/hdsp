package io.github.hdsp.dify.client.model.completion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.hdsp.dify.client.enums.ResponseMode;
import io.github.hdsp.dify.client.model.file.FileInfo;

/**
 * 文本生成请求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompletionRequest {
    /**
     * 输入参数，允许传入 App 定义的各变量值
     */
    @Builder.Default
    private Map<String, Object> inputs = new HashMap<>();

    /**
     * 响应模式
     */
    private ResponseMode responseMode;

    /**
     * 用户标识
     */
    private String user;

    /**
     * 文件列表
     */
    private List<FileInfo> files;
}

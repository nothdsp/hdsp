package io.github.hdsp.dify.client.model.chat;

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
 * 对话消息请求
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    /**
     * 用户输入/提问内容
     */
    private String query;

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
     * 会话 ID，需要基于之前的聊天记录继续对话，必须传之前消息的 conversation_id
     */
    private String conversationId;

    /**
     * 文件列表
     */
    private List<FileInfo> files;

    /**
     * 自动生成标题，默认 true
     */
    @Builder.Default
    private Boolean autoGenerateName = true;
}

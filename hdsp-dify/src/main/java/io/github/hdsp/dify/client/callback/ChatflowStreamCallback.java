package io.github.hdsp.dify.client.callback;

import io.github.hdsp.dify.client.event.*;

/**
 * 工作流编排对话型应用流式回调接口
 * 继承自 ChatStreamCallback，增加工作流相关回调方法
 */
public interface ChatflowStreamCallback extends ChatStreamCallback {
    /**
     * 工作流开始事件
     *
     * @param event 事件数据
     */
    default void onWorkflowStarted(WorkflowStartedEvent event) {
    }

    /**
     * 节点开始事件
     *
     * @param event 事件数据
     */
    default void onNodeStarted(NodeStartedEvent event) {
    }

    /**
     * 节点完成事件
     *
     * @param event 事件数据
     */
    default void onNodeFinished(NodeFinishedEvent event) {
    }

    /**
     * 工作流完成事件
     *
     * @param event 事件数据
     */
    default void onWorkflowFinished(WorkflowFinishedEvent event) {
    }

    /**
     * 迭代器开始执行事件
     *
     * @param event 事件数据
     */
    default void onIterationStarted(IterationStartedEvent event) {
    }

    /**
     * 迭代器下一次执行事件
     *
     * @param event 事件数据
     */
    default void onIterationNext(IterationNextEvent event) {
    }

    /**
     * 迭代器执行完成事件
     *
     * @param event 事件数据
     */
    default void onIterationCompleted(IterationCompletedEvent event) {
    }

    /**
     * 循环开始执行事件
     *
     * @param event 事件数据
     */
    default void onLoopStarted(LoopStartedEvent event) {
    }

    /**
     * 循环下一次执行事件
     *
     * @param event 事件数据
     */
    default void onLoopNext(LoopNextEvent event) {
    }

    /**
     * 循环执行完成事件
     *
     * @param event 事件数据
     */
    default void onLoopCompleted(LoopCompletedEvent event) {
    }
}

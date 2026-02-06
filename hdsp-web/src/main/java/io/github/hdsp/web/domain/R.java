package io.github.hdsp.web.domain;

import java.io.Serializable;

import io.github.hdsp.web.domain.enums.HttpStatus;
import io.github.hdsp.web.interceptor.HdspTraceIdInterceptor;
import lombok.Data;

/**
 * 统一响应结果类
 * 
 * @param <T> 数据类型
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 追踪ID
     */
    private String traceId;

    /**
     * 默认构造函数
     */
    public R() {
        this.timestamp = System.currentTimeMillis();
        this.traceId = HdspTraceIdInterceptor.getTraceId();
    }

    /**
     * 构造函数
     * 
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     */
    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        this.traceId = HdspTraceIdInterceptor.getTraceId();
    }

    /**
     * 成功返回默认状态码和消息
     * 
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> R<T> success() {
        return new R<>(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回数据
     * 
     * @param <T> 数据类型
     * @param data 数据
     * @return 响应结果
     */
    public static <T> R<T> success(T data) {
        return new R<>(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回消息和数据
     * 
     * @param <T> 数据类型
     * @param message 消息
     * @param data    数据
     * @return 响应结果
     */
    public static <T> R<T> success(String message, T data) {
        return new R<>(HttpStatus.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回默认状态码和消息
     * 
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> R<T> error() {
        return new R<>(HttpStatus.ERROR.getCode(), HttpStatus.ERROR.getMessage(), null);
    }

    /**
     * 失败返回消息
     * 
     * @param <T> 数据类型
     * @param message 消息
     * @return 响应结果
     */
    public static <T> R<T> error(String message) {
        return new R<>(HttpStatus.ERROR.getCode(), message, null);
    }

    /**
     * 失败返回状态码和消息
     * 
     * @param <T> 数据类型
     * @param code    状态码
     * @param message 消息
     * @return 响应结果
     */
    public static <T> R<T> error(Integer code, String message) {
        return new R<>(code, message, null);
    }

    /**
     * 失败返回状态码、消息和数据
     * 
     * @param <T> 数据类型
     * @param code    状态码
     * @param message 消息
     * @param data    数据
     * @return 响应结果
     */
    public static <T> R<T> error(Integer code, String message, T data) {
        return new R<>(code, message, data);
    }
}

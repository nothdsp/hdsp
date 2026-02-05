package io.github.hdsp.web.exception;

import io.github.hdsp.web.domain.enums.HttpStatus;
import lombok.Getter;

/**
 * 自定义业务异常类
 */
@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;

    /**
     * 默认构造函数
     */
    public BusinessException() {
        this.code = HttpStatus.BUSINESS_ERROR.getCode();
        this.message = HttpStatus.BUSINESS_ERROR.getMessage();
    }

    /**
     * 构造函数
     * 
     * @param message 消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = HttpStatus.BUSINESS_ERROR.getCode();
        this.message = message;
    }

    /**
     * 构造函数
     * 
     * @param code    状态码
     * @param message 消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数
     * 
     * @param resultCode 响应状态码枚举
     */
    public BusinessException(HttpStatus resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 构造函数
     * 
     * @param message 消息
     * @param cause   导致异常的Throwable对象
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = HttpStatus.BUSINESS_ERROR.getCode();
        this.message = message;
    }

    /**
     * 构造函数
     * 
     * @param code    状态码
     * @param message 消息
     * @param cause   导致异常的Throwable对象
     */
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}

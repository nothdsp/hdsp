package io.github.hdsp.web.exception;

import java.util.stream.Collectors;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.hdsp.web.domain.R;
import io.github.hdsp.web.domain.enums.HttpStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * 
     * @param ex 业务异常对象
     * @return 包含异常信息的响应对象
     */
    @ExceptionHandler(BusinessException.class)
    public R<Void> handleBusinessException(BusinessException ex) {
        log.error("业务异常: {}", ex.getMessage(), ex);
        return R.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理方法参数验证异常
     * 
     * @param ex 方法参数验证异常对象
     * @return 包含异常信息的响应对象
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数验证异常: {}", message, ex);
        return R.error(HttpStatus.PARAM_ERROR.getCode(), message);
    }

    /**
     * 处理绑定异常
     * 
     * @param ex 绑定异常对象
     * @return 包含异常信息的响应对象
     */
    @ExceptionHandler(BindException.class)
    public R<Void> handleBindException(BindException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数绑定异常: {}", message, ex);
        return R.error(HttpStatus.PARAM_ERROR.getCode(), message);
    }

    /**
     * 处理通用异常
     * 
     * @param ex 通用异常对象
     * @return 包含异常信息的响应对象
     */
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception ex) {
        log.error("系统异常: {}", ex.getMessage(), ex);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.getCode(), "系统异常，请稍后重试");
    }
}

package io.github.hdsp.redis.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.baomidou.lock.exception.LockFailureException;

import cn.hutool.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Redis异常处理器
 */
@Slf4j
@RestControllerAdvice
public class RedisExceptionHandler {

    /**
     * 分布式锁Lock4j异常
     */
    @ExceptionHandler(LockFailureException.class)
    public Map<String, Object> handleLockFailureException(LockFailureException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("获取锁失败了'{}',发生Lock4j异常.", requestURI, e);

        Map<String, Object> map = Map.of(
                "code", HttpStatus.HTTP_UNAVAILABLE,
                "message", "业务处理中，请稍后再试...",
                "timestamp", System.currentTimeMillis());
        return map;
    }

}

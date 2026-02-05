package io.github.hdsp.web.mybatisplus;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局公共数据填充器
 */
@Slf4j
@Component
public class GlobalMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时填充公共字段
     * 
     * @param metaObject MyBatis-Plus元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充...");
        this.strictInsertFill(metaObject, "createUserId", Long.class, 123456L);
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    /**
     * 更新时填充公共字段
     * 
     * @param metaObject MyBatis-Plus元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充...");
        this.strictUpdateFill(metaObject, "updateUserId", Long.class, 123456L);
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

}

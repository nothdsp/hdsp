package io.github.hdsp.redis.config.properties;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redisson 配置属性
 */
@Data
@ConfigurationProperties(prefix = "hdsp.redis.redisson")
public class RedissonProperties {

    /**
     * redis缓存key前缀
     */
    private String keyPrefix;

    /**
     * 线程池数量,默认值 = 当前处理核数量 * 2
     */
    private Integer threads;

    /**
     * Netty线程池数量,默认值 = 当前处理核数量 * 2
     */
    private Integer nettyThreads;

    /**
     * 单机服务配置。可不配 spring.data.redis，在此填写 address（或 host+port）、password、database 即可。
     */
    private SingleServerConfig singleServerConfig;

    /**
     * 集群服务配置。可不配 spring.data.redis，在此填写 nodeAddresses、password 即可。
     */
    private ClusterServersConfig clusterServersConfig;

    /**
     * 哨兵服务配置。可不配 spring.data.redis.sentinel，在此填写 masterName、sentinelAddresses 即可。
     */
    private SentinelServersConfig sentinelServersConfig;

    @Data
    @NoArgsConstructor
    public static class SingleServerConfig {

        /**
         * 单机地址，如 redis://127.0.0.1:6379（不配 spring.data.redis 时必填）
         */
        private String address;

        /**
         * 密码（不配 spring.data.redis 时可在此填写）
         */
        private String password;

        /**
         * 数据库索引，默认 0（不配 spring.data.redis 时可在此填写）
         */
        private Integer database;

        /**
         * 客户端名称
         */
        private String clientName;

        /**
         * 最小空闲连接数
         */
        private Integer connectionMinimumIdleSize;

        /**
         * 连接池大小
         */
        private Integer connectionPoolSize;

        /**
         * 连接空闲超时，单位：毫秒
         */
        private Integer idleConnectionTimeout;

        /**
         * 命令等待超时，单位：毫秒
         */
        private Integer timeout;

        /**
         * 发布和订阅连接池大小
         */
        private Integer subscriptionConnectionPoolSize;

    }

    @Data
    @NoArgsConstructor
    public static class ClusterServersConfig {

        /**
         * 集群节点地址列表，如 127.0.0.1:6379 或 redis://127.0.0.1:6379（不配 spring.data.redis 时必填）
         */
        private List<String> nodeAddresses;

        /**
         * 密码（不配 spring.data.redis 时可在此填写）
         */
        private String password;

        /**
         * 客户端名称
         */
        private String clientName;

        /**
         * master最小空闲连接数
         */
        private Integer masterConnectionMinimumIdleSize;

        /**
         * master连接池大小
         */
        private Integer masterConnectionPoolSize;

        /**
         * slave最小空闲连接数
         */
        private Integer slaveConnectionMinimumIdleSize;

        /**
         * slave连接池大小
         */
        private Integer slaveConnectionPoolSize;

        /**
         * 连接空闲超时，单位：毫秒
         */
        private Integer idleConnectionTimeout;

        /**
         * 命令等待超时，单位：毫秒
         */
        private Integer timeout;

        /**
         * 发布和订阅连接池大小
         */
        private Integer subscriptionConnectionPoolSize;

        /**
         * 读取模式
         */
        private ReadMode readMode;

        /**
         * 订阅模式
         */
        private SubscriptionMode subscriptionMode;

    }

    /**
     * 哨兵服务配置。
     * 方式一：配置 spring.data.redis.sentinel（master、nodes），此处仅做连接池/超时等可选调优。
     * 方式二：不配 spring.data.redis.sentinel 时，在此填写 masterName、sentinelAddresses 即可。
     */
    @Data
    @NoArgsConstructor
    public static class SentinelServersConfig {

        /**
         * 哨兵主节点名称（仅在不使用 spring.data.redis.sentinel 时必填）
         */
        private String masterName;

        /**
         * 哨兵节点地址列表，格式 host:port（仅在不使用 spring.data.redis.sentinel 时必填）
         */
        private List<String> sentinelAddresses;

        /**
         * Redis 主从密码（连接 master/slave 时使用）
         */
        private String password;

        /**
         * 哨兵节点密码（sentinel.conf 中 requirepass，Redis 5+）
         */
        private String sentinelPassword;

        /**
         * 客户端名称
         */
        private String clientName;

        /**
         * master 最小空闲连接数
         */
        private Integer masterConnectionMinimumIdleSize;

        /**
         * master 连接池大小
         */
        private Integer masterConnectionPoolSize;

        /**
         * slave 最小空闲连接数
         */
        private Integer slaveConnectionMinimumIdleSize;

        /**
         * slave 连接池大小
         */
        private Integer slaveConnectionPoolSize;

        /**
         * 连接空闲超时，单位：毫秒
         */
        private Integer idleConnectionTimeout;

        /**
         * 命令等待超时，单位：毫秒
         */
        private Integer timeout;

        /**
         * 发布和订阅连接池大小
         */
        private Integer subscriptionConnectionPoolSize;

        /**
         * 读取模式
         */
        private ReadMode readMode;

        /**
         * 订阅模式
         */
        private SubscriptionMode subscriptionMode;

    }

}

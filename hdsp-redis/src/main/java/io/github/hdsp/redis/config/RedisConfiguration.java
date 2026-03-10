package io.github.hdsp.redis.config;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.codec.CompositeCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.redisson.config.SentinelServersConfig;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.thread.Threading;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.task.VirtualThreadTaskExecutor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.hdsp.redis.config.properties.RedissonProperties;
import io.github.hdsp.redis.handler.KeyPrefixHandler;
import io.github.hdsp.redis.handler.RedisExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * redis配置
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedisConfiguration {

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    public RedissonAutoConfigurationCustomizer redissonCustomizer() {
        return config -> {
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
            ObjectMapper om = new ObjectMapper();
            om.registerModule(javaTimeModule);
            om.setTimeZone(TimeZone.getDefault());
            om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            // 指定序列化输入的类型，类必须是非final修饰的。序列化时将对象全类名一起保存下来
            om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
            // org.apache.fory.logging.LoggerFactory 包别引入错了
            // LoggerFactory.useSlf4jLogging(true);
            // ForyCodec foryCodec = new ForyCodec();
            // CompositeCodec codec = new CompositeCodec(StringCodec.INSTANCE, foryCodec, foryCodec);
            TypedJsonJacksonCodec jsonCodec = new TypedJsonJacksonCodec(Object.class, om);
            // 组合序列化 key 使用 String 内容使用通用 json 格式
            CompositeCodec codec = new CompositeCodec(StringCodec.INSTANCE, jsonCodec, jsonCodec);
            config.setThreads(redissonProperties.getThreads())
                .setNettyThreads(redissonProperties.getNettyThreads())
                // 缓存 Lua 脚本 减少网络传输(redisson 大部分的功能都是基于 Lua 脚本实现)
                .setUseScriptCache(true)
                .setCodec(codec);
            if (isVirtual()) {
                config.setNettyExecutor(new VirtualThreadTaskExecutor("redisson-"));
            }
            // 哨兵模式：由 Spring 根据 spring.data.redis.sentinel 已创建时仅增强；否则由本模块创建
            SentinelServersConfig existingSentinel = getSentinelServersConfig(config);
            RedissonProperties.SentinelServersConfig sentinelServersConfig = redissonProperties.getSentinelServersConfig();
            if (ObjectUtil.isNotNull(existingSentinel)) {
                existingSentinel.setNameMapper(new KeyPrefixHandler(redissonProperties.getKeyPrefix()));
                if (ObjectUtil.isNotNull(sentinelServersConfig)) {
                    existingSentinel.setTimeout(sentinelServersConfig.getTimeout())
                        .setClientName(sentinelServersConfig.getClientName())
                        .setIdleConnectionTimeout(sentinelServersConfig.getIdleConnectionTimeout())
                        .setSubscriptionConnectionPoolSize(sentinelServersConfig.getSubscriptionConnectionPoolSize())
                        .setMasterConnectionMinimumIdleSize(sentinelServersConfig.getMasterConnectionMinimumIdleSize())
                        .setMasterConnectionPoolSize(sentinelServersConfig.getMasterConnectionPoolSize())
                        .setSlaveConnectionMinimumIdleSize(sentinelServersConfig.getSlaveConnectionMinimumIdleSize())
                        .setSlaveConnectionPoolSize(sentinelServersConfig.getSlaveConnectionPoolSize())
                        .setReadMode(sentinelServersConfig.getReadMode())
                        .setSubscriptionMode(sentinelServersConfig.getSubscriptionMode());
                    if (StrUtil.isNotBlank(sentinelServersConfig.getPassword())) {
                        existingSentinel.setPassword(sentinelServersConfig.getPassword());
                    }
                    if (StrUtil.isNotBlank(sentinelServersConfig.getSentinelPassword())) {
                        existingSentinel.setSentinelPassword(sentinelServersConfig.getSentinelPassword());
                    }
                }
            } else if (ObjectUtil.isNotNull(sentinelServersConfig)
                && StrUtil.isNotBlank(sentinelServersConfig.getMasterName())
                && sentinelServersConfig.getSentinelAddresses() != null
                && !sentinelServersConfig.getSentinelAddresses().isEmpty()) {
                var sentinel = config.useSentinelServers()
                    .setNameMapper(new KeyPrefixHandler(redissonProperties.getKeyPrefix()))
                    .setMasterName(sentinelServersConfig.getMasterName())
                    .addSentinelAddress(sentinelServersConfig.getSentinelAddresses().toArray(new String[0]))
                    .setTimeout(sentinelServersConfig.getTimeout())
                    .setClientName(sentinelServersConfig.getClientName())
                    .setIdleConnectionTimeout(sentinelServersConfig.getIdleConnectionTimeout())
                    .setSubscriptionConnectionPoolSize(sentinelServersConfig.getSubscriptionConnectionPoolSize())
                    .setMasterConnectionMinimumIdleSize(sentinelServersConfig.getMasterConnectionMinimumIdleSize())
                    .setMasterConnectionPoolSize(sentinelServersConfig.getMasterConnectionPoolSize())
                    .setSlaveConnectionMinimumIdleSize(sentinelServersConfig.getSlaveConnectionMinimumIdleSize())
                    .setSlaveConnectionPoolSize(sentinelServersConfig.getSlaveConnectionPoolSize())
                    .setReadMode(sentinelServersConfig.getReadMode())
                    .setSubscriptionMode(sentinelServersConfig.getSubscriptionMode());
                if (StrUtil.isNotBlank(sentinelServersConfig.getPassword())) {
                    sentinel.setPassword(sentinelServersConfig.getPassword());
                }
                if (StrUtil.isNotBlank(sentinelServersConfig.getSentinelPassword())) {
                    sentinel.setSentinelPassword(sentinelServersConfig.getSentinelPassword());
                }
            }
            // 非哨兵模式时才使用单机/集群
            RedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
            if (ObjectUtil.isNotNull(singleServerConfig) && getSentinelServersConfig(config) == null) {
                var single = config.useSingleServer()
                    .setNameMapper(new KeyPrefixHandler(redissonProperties.getKeyPrefix()))
                    .setTimeout(singleServerConfig.getTimeout())
                    .setClientName(singleServerConfig.getClientName())
                    .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
                    .setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
                    .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
                    .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize());
                // 不使用 spring.data.redis 时，由本配置提供连接信息
                if (StrUtil.isNotBlank(singleServerConfig.getAddress())) {
                    single.setAddress(normalizeRedisAddress(singleServerConfig.getAddress()));
                }
                if (singleServerConfig.getPassword() != null) {
                    single.setPassword(singleServerConfig.getPassword());
                }
                if (singleServerConfig.getDatabase() >= 0) {
                    single.setDatabase(singleServerConfig.getDatabase());
                }
            }
            RedissonProperties.ClusterServersConfig clusterServersConfig = redissonProperties.getClusterServersConfig();
            if (ObjectUtil.isNotNull(clusterServersConfig) && getSentinelServersConfig(config) == null) {
                var cluster = config.useClusterServers()
                    .setNameMapper(new KeyPrefixHandler(redissonProperties.getKeyPrefix()))
                    .setTimeout(clusterServersConfig.getTimeout())
                    .setClientName(clusterServersConfig.getClientName())
                    .setIdleConnectionTimeout(clusterServersConfig.getIdleConnectionTimeout())
                    .setSubscriptionConnectionPoolSize(clusterServersConfig.getSubscriptionConnectionPoolSize())
                    .setMasterConnectionMinimumIdleSize(clusterServersConfig.getMasterConnectionMinimumIdleSize())
                    .setMasterConnectionPoolSize(clusterServersConfig.getMasterConnectionPoolSize())
                    .setSlaveConnectionMinimumIdleSize(clusterServersConfig.getSlaveConnectionMinimumIdleSize())
                    .setSlaveConnectionPoolSize(clusterServersConfig.getSlaveConnectionPoolSize())
                    .setReadMode(clusterServersConfig.getReadMode())
                    .setSubscriptionMode(clusterServersConfig.getSubscriptionMode());
                // 不使用 spring.data.redis 时，由本配置提供节点地址
                if (clusterServersConfig.getNodeAddresses() != null && !clusterServersConfig.getNodeAddresses().isEmpty()) {
                    String[] addrs = clusterServersConfig.getNodeAddresses().stream()
                        .map(RedisConfiguration::normalizeRedisAddress)
                        .toArray(String[]::new);
                    cluster.addNodeAddress(addrs);
                }
                if (StrUtil.isNotBlank(clusterServersConfig.getPassword())) {
                    cluster.setPassword(clusterServersConfig.getPassword());
                }
            }
            if (getSentinelServersConfig(config) != null) {
                log.info("初始化 redis 配置（哨兵模式）");
            } else {
                log.info("初始化 redis 配置");
            }
        };
    }

    /**
     * 异常处理器
     */
    @Bean
    public RedisExceptionHandler redisExceptionHandler() {
        return new RedisExceptionHandler();
    }

    public static boolean isVirtual() {
        return Threading.VIRTUAL.isActive(SpringUtil.getBean(Environment.class));
    }

    /**
     * 通过反射获取 Config 的哨兵配置（getSentinelServersConfig 为 protected）
     */
    private static SentinelServersConfig getSentinelServersConfig(Config config) {
        try {
            Method m = Config.class.getDeclaredMethod("getSentinelServersConfig");
            m.setAccessible(true);
            return (SentinelServersConfig) m.invoke(config);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 地址若未带 redis:// 或 rediss:// 则补上 redis://
     */
    static String normalizeRedisAddress(String address) {
        if (StrUtil.isBlank(address)) {
            return address;
        }
        String s = address.trim();
        if (s.startsWith("rediss://") || s.startsWith("redis://")) {
            return s;
        }
        return "redis://" + s;
    }

    /**
     * redis集群配置 yml
     *
     * --- # redis 集群配置(单机与集群只能开启一个另一个需要注释掉)
     * spring.data:
     *   redis:
     *     cluster:
     *       nodes:
     *         - 192.168.0.100:6379
     *         - 192.168.0.101:6379
     *         - 192.168.0.102:6379
     *     # 密码
     *     password:
     *     # 连接超时时间
     *     timeout: 10s
     *     # 是否开启ssl
     *     ssl.enabled: false
     *
     * hdsp.redis.redisson:
     *   # 线程池数量
     *   threads: 16
     *   # Netty线程池数量
     *   nettyThreads: 32
     *   # 集群配置
     *   clusterServersConfig:
     *     # 客户端名称
     *     clientName: ${ruoyi.name}
     *     # master最小空闲连接数
     *     masterConnectionMinimumIdleSize: 32
     *     # master连接池大小
     *     masterConnectionPoolSize: 64
     *     # slave最小空闲连接数
     *     slaveConnectionMinimumIdleSize: 32
     *     # slave连接池大小
     *     slaveConnectionPoolSize: 64
     *     # 连接空闲超时，单位：毫秒
     *     idleConnectionTimeout: 10000
     *     # 命令等待超时，单位：毫秒
     *     timeout: 3000
     *     # 发布和订阅连接池大小
     *     subscriptionConnectionPoolSize: 50
     *     # 读取模式
     *     readMode: "SLAVE"
     *     # 订阅模式
     *     subscriptionMode: "MASTER"
     *
     * --- # redis 哨兵模式（与单机/集群二选一）
     * spring.data:
     *   redis:
     *     sentinel:
     *       master: mymaster
     *       nodes:
     *         - 127.0.0.1:26379
     *         - 127.0.0.1:26380
     *     password: your-password
     *     timeout: 10s
     * hdsp.redis.redisson:
     *   key-prefix: myapp
     *   sentinel-servers-config:
     *     client-name: my-app
     *     master-connection-minimum-idle-size: 32
     *     master-connection-pool-size: 64
     *     slave-connection-minimum-idle-size: 32
     *     slave-connection-pool-size: 64
     *     idle-connection-timeout: 10000
     *     timeout: 3000
     *     subscription-connection-pool-size: 50
     *     read-mode: SLAVE
     *     subscription-mode: MASTER
     */

}

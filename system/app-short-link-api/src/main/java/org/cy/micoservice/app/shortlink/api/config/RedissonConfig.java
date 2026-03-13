package org.cy.micoservice.app.shortlink.api.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SubscriptionMode;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedissonConfig {

  private String password;
  private Cluster cluster;

  @Data
  public static class Cluster {
    private List<String> nodes;
    private int maxRedirects = 3;
  }

  @Autowired
  private ShortLinkApiProperties properties;

  // todo: redis集群需要配置此项
  @Bean
  public RedissonClient redissonClient() {
    Config config = new Config();

    String[] nodes = cluster.getNodes().stream()
      .map(node -> "redis://" + node)
      .toArray(String[]::new);

    config.useClusterServers()
      .addNodeAddress(nodes)
      .setPassword(password)
      // 连接池配置 - 针对集群分片优化
      .setMasterConnectionMinimumIdleSize(20)
      .setMasterConnectionPoolSize(100)
      .setSlaveConnectionMinimumIdleSize(20)
      .setSlaveConnectionPoolSize(100)
      // 超时配置
      .setIdleConnectionTimeout(10000)
      .setConnectTimeout(properties.getConnectionTimeout())
      .setTimeout(properties.getSocketTimeout())
      // 重试配置
      .setRetryAttempts(3)
      .setRetryInterval(1500)
      // 集群扫描配置
      .setScanInterval(2000)
      // 读写模式配置 - 支持读写分离
      .setReadMode(properties.isEnableReadWriteSplit() ? ReadMode.SLAVE : ReadMode.MASTER)
      .setSubscriptionMode(SubscriptionMode.MASTER)
      // 负载均衡
      .setLoadBalancer(new RoundRobinLoadBalancer())
      // 集群故障转移
      .setFailedSlaveReconnectionInterval(3000)
      .setFailedSlaveCheckInterval(60000)
      // 启用集群拓扑刷新
      .setCheckSlotsCoverage(false)
      // 集群分片优化
      .setPingConnectionInterval(30000)
      .setKeepAlive(true);

    return Redisson.create(config);
  }
}
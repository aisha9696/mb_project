package kz.mb.project.mb_project.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
@EnableCaching
public class CacheConfiguration {

  @Value("${spring.data.redis.def-ttl}")
  private long defTokenTtl;

  @Bean("defTokenCacheManager")
  public CacheManager defTokenCacheManager(RedisConnectionFactory redisConnectionFactory) {
    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(cacheConfiguration(defTokenTtl))
        .build();
  }

  @Bean("dayCacheManager")
  @Primary
  public CacheManager dayCacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofDays(1))
        .serializeValuesWith(getValueSerializationPair());
    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(configuration)
        .build();
  }

  @Bean("defaultCacheManager")
  public CacheManager defaultCacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(30))
        .serializeValuesWith(getValueSerializationPair());
    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(configuration)
        .build();
  }

  private static RedisCacheConfiguration cacheConfiguration(Long duration) {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(duration))
        .serializeValuesWith(getValueSerializationPair());
  }

  private static SerializationPair<Object> getValueSerializationPair() {
    return SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());
  }

}
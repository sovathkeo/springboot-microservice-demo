package kh.com.cellcard.common.configurations.caching;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
public class RedisConfiguration {
    @Value("${spring.redis.host:localhost}")
    private String REDIS_HOST;

    @Value("${spring.redis.port:6379}")
    private String REDIS_PORT;

    @Value("${spring.redis.password:admin}")
    private String REDIS_PASSWORD;

    @Value("${spring.redis.timeout:5000}")
    private String REDIS_TIMEOUT;

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        return template;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        SocketOptions socketOptions = SocketOptions.builder().connectTimeout(Duration.ofMillis(Long.parseLong(REDIS_TIMEOUT))).build();
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientOptions(ClientOptions.builder().socketOptions(socketOptions).build())
                .build();

        return new LettuceConnectionFactory(customRedisStandaloneConfiguration(), clientConfig);
    }

    private RedisStandaloneConfiguration customRedisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(REDIS_HOST);
        redisStandaloneConfiguration.setPort(Integer.parseInt(REDIS_PORT));
        redisStandaloneConfiguration.setPassword(REDIS_PASSWORD);

        // Set other configurations if needed
        return redisStandaloneConfiguration;
    }
}
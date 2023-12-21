package kh.com.cellcard.services.caching;

import kh.com.cellcard.common.helper.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class CachingService {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private RedisTemplate<String, String> redisTemplateString;
    private RedisTemplate<String, Object> redisTemplateObject;


    public Optional<String> getString(String key) {
        if (StringHelper.isNullOrEmpty(key)) {
            return Optional.empty();
        }
        return Optional.ofNullable(redisTemplateString.opsForValue().get(key));
    }

    public void setString(String key, String value,long lifeTime, TimeUnit timeUnit) {
        if (!isConnectedToRedis() || StringHelper.isNullOrEmpty(key) || StringHelper.isNullOrEmpty(value)) {
            return;
        }
        redisTemplateString.opsForValue().set(key, value,lifeTime, timeUnit);
    }

    public boolean isConnectedToRedis() {
        try {
            var connection = redisConnectionFactory.getConnection();
            return Objects.requireNonNull(connection.ping()).equalsIgnoreCase("PONG");
        } catch (Exception e) {
            if(e instanceof RedisConnectionFailureException) {
                throw e;
            }
            return false;
        }
    }
}

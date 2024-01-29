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

    public Optional<Object> getForHash(String key, Object hashKey){
        if (isNullKeyOrDisconnected(key)) {
            return Optional.empty();
        }
        return Optional.ofNullable(redisTemplateString.opsForHash().get(key, hashKey));
    }

    public boolean setForHash(String key, Object hashKey, Object value){
        if (isNullKeyOrDisconnected(key)) {
            return false;
        }
        redisTemplateString.opsForHash().put(key, hashKey, value);
        return true;
    }

    public void setString(String key, String value,long lifeTime, TimeUnit timeUnit) {
        if (!isConnectedToRedis() || StringHelper.isNullOrEmpty(key) || StringHelper.isNullOrEmpty(value)) {
            return;
        }
        redisTemplateString.opsForValue().set(key, value,lifeTime, timeUnit);
    }

    public Long getExpire(String key) {
        if (isNullKeyOrDisconnected(key)) {
            return (long) -1;
        }
        return redisTemplateString.getExpire(key);
    }

    public void setExpire(String key, long lifeTime, TimeUnit timeUnit) {
        if (isNullKeyOrDisconnected(key)) {
            return;
        }
        redisTemplateString.expire(key, lifeTime, timeUnit);
    }

    public boolean isConnectedToRedis() {
        try {
            var connection = redisConnectionFactory.getConnection();
            return Objects.requireNonNull(connection.ping()).equalsIgnoreCase("PONG");
        } catch (Exception e) {
            if(e instanceof RedisConnectionFailureException) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private boolean isNullKeyOrDisconnected(String key) {
        return StringHelper.isNullOrEmpty(key) || !isConnectedToRedis();
    }
}

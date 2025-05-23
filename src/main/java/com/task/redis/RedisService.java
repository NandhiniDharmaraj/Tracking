package com.task.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	public boolean isDuplicate(String key, long ttlInSeconds) {
		Boolean isNew = redisTemplate.opsForValue().setIfAbsent(key, "1", ttlInSeconds, TimeUnit.SECONDS);
		return Boolean.FALSE.equals(isNew);
	}

}

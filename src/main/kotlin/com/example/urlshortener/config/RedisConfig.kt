package com.example.urlshortener.config

import com.example.urlshortener.model.UrlMapping
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig : CachingConfigurerSupport(){
    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Autowired
    private val redisConnectionFactory: RedisConnectionFactory? = null

    @Value("\${redis.host-name}")
    private val redisHostName: String? = null

    @Value("\${redis.port}")
    private val redisPort: Int? = null




    // Setting up the redis template object.
    @Bean
    fun redisTemplate(): RedisTemplate<String, UrlMapping>? {
        val jackson2JsonRedisSerializer: Jackson2JsonRedisSerializer<*> =
            Jackson2JsonRedisSerializer(UrlMapping::class.java)
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper!!)
        val redisTemplate: RedisTemplate<String, UrlMapping> = RedisTemplate<String, UrlMapping>()
        redisTemplate.setConnectionFactory(redisConnectionFactory!!)
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = jackson2JsonRedisSerializer
        return redisTemplate
    }


}
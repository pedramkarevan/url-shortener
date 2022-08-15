package com.example.urlshortener

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories
import org.springframework.web.client.RestTemplate



@SpringBootApplication
@EnableCassandraRepositories("com.example.urlshortener.repository")
class UrlShortenerApplication

fun main(args: Array<String>) {
    runApplication<UrlShortenerApplication>(*args)
}


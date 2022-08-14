package com.example.urlshortener.services

import com.example.urlshortener.model.UrlMapping
import com.example.urlshortener.repository.UrlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service


@Service
class UrlMappingService {

    @Autowired
    private val urlMapRepo: UrlRepository? = null

    @Cacheable(value = ["UrlMapping"], key = "#UrlMapping.newUrl")
    fun getByNewUrl(newUrl: String?): UrlMapping?{
        return urlMapRepo?.findById(newUrl.toString())?.orElse(null)
    }

    @CachePut(value = ["UrlMapping"], key = "#UrlMapping.newUrl")
    fun save(urlMapping: UrlMapping): UrlMapping {
        return urlMapRepo!!.save(urlMapping)
    }


}
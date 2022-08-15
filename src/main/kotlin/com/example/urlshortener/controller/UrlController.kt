package com.example.urlshortener.controller

import com.example.urlshortener.model.UrlMapping
import com.example.urlshortener.services.UrlGeneratorService
import com.example.urlshortener.services.UrlMappingService
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.extern.slf4j.Slf4j
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletResponse

@Slf4j
@RestController
@RequestMapping(value = ["/urls/"])
class UrlController {

    @Autowired
    private val urlGeneratorService: UrlGeneratorService? = null

    @Autowired
    private val urlMappingService: UrlMappingService? = null

    @Autowired
    private val redisTemplate: RedisTemplate<String, UrlMapping>? = null

    @Value("\${redis.ttl}")
    private val ttl: Long = 0

    @RequestMapping(value = ["/generate"], method = [RequestMethod.POST])
    @ResponseBody
    fun generateURL(@RequestBody url: String?): ResponseEntity<*>? {
        val urlValidator = UrlValidator(arrayOf<String>("http", "https"))
        if (!urlValidator.isValid(url))
            return ResponseEntity.badRequest().body("Invalid URL format.")
        val urlObject: UrlMapping = UrlMapping(urlGeneratorService!!.getNewURL(url)!!, url!!)
        var tinyUrl = urlMappingService?.save(urlObject)
        redisTemplate!!.opsForValue()[tinyUrl!!.newUrl.toString(), urlObject, ttl] = TimeUnit.SECONDS
        return ResponseEntity.ok<Any>("localhost:8080/urls/" + urlObject.newUrl)
    }

    @RequestMapping(value = ["/{tinyUrl}"], method = [RequestMethod.GET])
    fun getOriginalUrl(@PathVariable(value = "tinyUrl") sTinyUrl: String?, response: HttpServletResponse) {
        try {
            val urlMap = urlMappingService?.getByNewUrl(sTinyUrl)
            if (urlMap != null)
                response.sendRedirect(urlMap.oldUrl)
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Shortened URL does not generated.")
        } catch (e: Exception) {
            e.printStackTrace();
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error!");
        }


    }
}


/*@Autowired
private val redisTemplate: RedisTemplate<String, UrlDto>? = null

@Value("\${redis.ttl}")
private val ttl: Long = 0

@PostMapping
fun create(@RequestBody url: String?): ResponseEntity<*>? {
    val urlValidator = UrlValidator(arrayOf("http:", "https:"))
    if (!urlValidator.isValid(url)) {
        return ResponseEntity.badRequest().body(InvalidURLException("Invalid URL."))
    }
    val urlDto: UrlDto = UrlDto.create(url)
    log.println("URL id generated = {}" +urlDto.id)
    redisTemplate!!.opsForValue()[urlDto.id.toString(), urlDto, ttl] = TimeUnit.SECONDS
    return ResponseEntity.noContent().header("id", urlDto.id).build<Any>()
}

@GetMapping(value = ["/{id}"])
fun getUrl(@PathVariable id: String?): ResponseEntity<*>? {
    val urlDto: UrlDto? = redisTemplate!!.opsForValue()[id!!]
    if (Objects.isNull(urlDto)) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UrlNotFoundException("No such key exists."))
    } else {
        if (urlDto != null) {
            log.println("URL retrieved = {}"+ urlDto.url)
        }
    }
    return ResponseEntity.ok<Any>(urlDto)
}*/

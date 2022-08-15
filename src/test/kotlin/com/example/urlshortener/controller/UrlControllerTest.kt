package com.example.urlshortener.controller


import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class UrlControllerTest {

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    //localhost:8080/urls/aaaaanI
    //https://google.com
    //assertEquals(result.body, "Hello string!")

    @Test
    @DisplayName("CallWithWrongTinyURL")
    fun whenGetCalled_thenShouldBadRequest() {
        val result = restTemplate.getForEntity("/urls/aaaaa",String::class.java);

        assertNotNull(result)
        assertEquals(result.statusCode, HttpStatus.BAD_REQUEST)
    }

    //For test there should be a tinyurl generated in DB
    @Test
    @DisplayName("CallWithTinyURLAlreadyExist")
    fun whenGetCalled_thenShouldRedirectToLongURL() {
        val result = restTemplate.getForEntity("/urls/aaaaanI",String::class.java);

        assertNotNull(result.statusCodeValue)
        println("statusCode: "+result.statusCode.value())
        assertEquals(result.statusCodeValue, HttpStatus.FOUND.value())
    }

    @Test
    @DisplayName("CreateTinyUrlWithInvalidStringFormat")
    fun whenGenerateCalledWithInvalidFormat() {
        val result = restTemplate.postForEntity("/urls/generate","http//google.com",String::class.java)

        assertNotNull(result.statusCodeValue)
        assertEquals(result.statusCode, HttpStatus.BAD_REQUEST)
        assertEquals(result.body, "Invalid URL format.")
    }

    @Test
    @DisplayName("CreateTinyUrl")
    fun whenGenerateEndPointCall() {
        val result = restTemplate.postForEntity("/urls/generate","http://google.com",String::class.java)

        assertNotNull(result.statusCodeValue)
        assertEquals(result.statusCode, HttpStatus.OK)

    }
}
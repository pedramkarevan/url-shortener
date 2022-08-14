package com.example.urlshortener.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler

class UrlNotFoundException(message: String) : RuntimeException(message) {

    @ExceptionHandler
    fun handleTinyUrlNotFoundExceptionException(ex: UrlNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ErrorCodes.ERROR_CODE_URL_DOES_NOT_EXIST,
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
}
package com.example.urlshortener.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler


class InvalidURLException (message: String) : RuntimeException(message){

    @ExceptionHandler
    fun handleInvalidUrlFormatException(ex: InvalidURLException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            ErrorCodes.ERROR_CODE_INVALID_URL,
            ex.message
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }


}

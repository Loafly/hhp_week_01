package io.hhplus.tdd

import io.hhplus.tdd.exception.InvalidAmountRequestException
import io.hhplus.tdd.exception.InvalidPointAmountException
import io.hhplus.tdd.exception.NotEnoughPointsException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

data class ErrorResponse(val code: String, val message: String)

@RestControllerAdvice
class ApiControllerAdvice : ResponseEntityExceptionHandler() {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse("500", "에러가 발생했습니다."),
            HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }

    @ExceptionHandler(value = [
        IllegalArgumentException::class,
        NotEnoughPointsException::class,
        InvalidPointAmountException::class,
        InvalidAmountRequestException::class
    ])
    fun handleCommonExceptions(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e.message)

        val statusCode = HttpStatus.BAD_REQUEST.value()
        return ResponseEntity(
            ErrorResponse(statusCode.toString(), e.message.toString()),
            HttpStatus.BAD_REQUEST,
        )
    }

}
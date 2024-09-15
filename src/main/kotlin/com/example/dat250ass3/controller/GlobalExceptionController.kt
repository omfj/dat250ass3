package com.example.dat250ass3.controller

import com.example.dat250ass3.exception.NotSignedInException
import com.example.dat250ass3.exception.PollExpiredException
import com.example.dat250ass3.exception.PollNotFoundException
import com.example.dat250ass3.exception.PollVoteOptionNotFoundException
import com.example.dat250ass3.exception.UnauthorizedException
import com.example.dat250ass3.model.output.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionController {
    @ExceptionHandler(PollExpiredException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlePollExpiredException(e: PollExpiredException): ErrorResponse {
        return ErrorResponse(e.message!!)
    }

    @ExceptionHandler(PollNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlePollNotFoundException(e: PollNotFoundException): ErrorResponse {
        return ErrorResponse(e.message!!)
    }

    @ExceptionHandler(PollVoteOptionNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlePollVoteOptionNotFoundException(e: PollVoteOptionNotFoundException): ErrorResponse {
        return ErrorResponse(e.message!!)
    }

    @ExceptionHandler(NotSignedInException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleNotSignedInException(e: NotSignedInException): ErrorResponse {
        return ErrorResponse(e.message!!)
    }

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(e: UnauthorizedException): ErrorResponse {
        return ErrorResponse(e.message!!)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(): ErrorResponse {
        return ErrorResponse("Unknown error occurred")
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(): ErrorResponse {
        return ErrorResponse("Invalid request body")
    }
}

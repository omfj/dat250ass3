package com.example.dat250ass2.controller

import com.example.dat250ass2.exception.PollExpiredException
import com.example.dat250ass2.exception.PollNotFoundException
import com.example.dat250ass2.exception.PollVoteOptionNotFoundException
import com.example.dat250ass2.model.output.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionController {
    @ExceptionHandler(PollExpiredException::class)
    fun handlePollExpiredException(e: PollExpiredException): ErrorResponse {
        return ErrorResponse(e.message!!)
    }

    @ExceptionHandler(PollNotFoundException::class)
    fun handlePollNotFoundException(e: PollNotFoundException): ErrorResponse {
        return ErrorResponse(e.message!!)
    }

    @ExceptionHandler(PollVoteOptionNotFoundException::class)
    fun handlePollVoteOptionNotFoundException(e: PollVoteOptionNotFoundException): ErrorResponse {
        return ErrorResponse(e.message!!)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(): ErrorResponse {
        return ErrorResponse("Unknown error occurred")
    }
}

package ru.dankos.api.intradaytradingmanagement.controller.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.dankos.api.intradaytradingmanagement.service.exception.EntityNotFoundException
import ru.dankos.api.intradaytradingmanagement.service.exception.NotSupportedNumberQueryParamException
import ru.dankos.api.intradaytradingmanagement.service.exception.NotSupportedQueryParamException
import ru.dankos.api.intradaytradingmanagement.service.exception.ServiceException
import java.util.*


@ControllerAdvice
class GenericExceptionHandler {

    @ExceptionHandler(value = [ServiceException::class])
    internal fun handleServiceException(exception: ServiceException) =
        ResponseEntity<ErrorResponse>(
            buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR
        )

    @ExceptionHandler(value = [NotSupportedNumberQueryParamException::class, NotSupportedQueryParamException::class])
    internal fun handleNotSupportedQueryParamException(exception: ServiceException) =
        ResponseEntity<ErrorResponse>(
            buildErrorResponse(exception, HttpStatus.BAD_REQUEST),
            HttpStatus.BAD_REQUEST
        )

    @ExceptionHandler(value = [EntityNotFoundException::class])
    internal fun handleEntityNotFoundException(exception: EntityNotFoundException) =
        ResponseEntity<ErrorResponse>(
            buildErrorResponse(exception, HttpStatus.NOT_FOUND),
            HttpStatus.NOT_FOUND
        )

    private fun buildErrorResponse(exception: ServiceException, httpStatus: HttpStatus): ErrorResponse = ErrorResponse(
        message = exception.message,
        timestamp = Date(),
        errorCode = httpStatus.value(),
        errorMessage = httpStatus.name,
    )
}

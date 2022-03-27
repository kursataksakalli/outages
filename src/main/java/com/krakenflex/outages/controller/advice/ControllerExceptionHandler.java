package com.krakenflex.outages.controller.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.krakenflex.outages.dto.BaseResponseDto;
import com.krakenflex.outages.exception.UnavailableServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

/**
 * exception handler at controller level
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(
            value = HttpStatus.INTERNAL_SERVER_ERROR,
            reason = "There is a problem at Json processing!"
    )
    public BaseResponseDto<Void> jsonProcessingException(JsonProcessingException ex, WebRequest request) {
        BaseResponseDto result = new BaseResponseDto();
        result.setResponseCode(500);
        result.setErrors(new ArrayList<>());
        result.getErrors().add("There is a problem at Json processing!");

        return result;
    }

    @ExceptionHandler(UnavailableServiceException.class)
    @ResponseStatus(
            value = HttpStatus.SERVICE_UNAVAILABLE
    )
    public BaseResponseDto<Void> unavailableServiceException(UnavailableServiceException ex, WebRequest request) {
        BaseResponseDto result = new BaseResponseDto();
        result.setResponseCode(503);
        result.setErrors(new ArrayList<>());
        result.getErrors().add(ex.getMessage());

        return result;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(
            value = HttpStatus.INTERNAL_SERVER_ERROR
    )
    public BaseResponseDto<Void> runtimeException(RuntimeException ex, WebRequest request) {
        BaseResponseDto result = new BaseResponseDto();

        if(ex instanceof UnavailableServiceException) {
            result.setResponseCode(503);
            result.setErrors(new ArrayList<>());
            result.getErrors().add(ex.getMessage());
        } else {
            result.setResponseCode(500);
            result.setErrors(new ArrayList<>());
            result.getErrors().add("There is a problem at server");
        }

        return result;
    }

}
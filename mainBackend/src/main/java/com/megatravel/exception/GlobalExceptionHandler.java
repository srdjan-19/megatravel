package com.megatravel.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BadRequestException.class)
    public void handleBadRequestException(HttpServletResponse response, BadRequestException e) throws IOException {
        LOG.error("ERROR", e);
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
    
    @ExceptionHandler(ExceptionResponse.class)
    public void handleCustomException(HttpServletResponse response, ExceptionResponse e) throws IOException {
        LOG.error("ERROR", e);
        response.sendError(e.getHttpStatus().value(), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse response, AccessDeniedException e) throws IOException {
        LOG.error("ERROR", e);
        response.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(HttpServletResponse response, IllegalArgumentException e) throws IOException {
       // LOG.error("ERROR", e);
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong! \nIllegal arguments!");
    }

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse response, Exception e) throws IOException {
       // LOG.error("ERROR", e);
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
    }


}


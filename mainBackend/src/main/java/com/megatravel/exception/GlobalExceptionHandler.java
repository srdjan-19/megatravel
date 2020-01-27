package com.megatravel.exception;

import java.io.IOException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BadRequestException.class)
    public void handleBadRequestException(HttpServletResponse response, BadRequestException e) throws IOException {
        LOG.error("ERROR", e);
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(HttpServletResponse response, EntityNotFoundException e) throws IOException {
        LOG.error("ERROR", e);
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(EntityExistsException.class)
    public void handleEntityAlreadyExistException(HttpServletResponse response, EntityExistsException e) throws IOException {
        LOG.error("ERROR", e);
        response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse response, AccessDeniedException e) throws IOException {
        LOG.error("ERROR", e);
        response.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
    }
    
    @ExceptionHandler(RollbackException.class)
    public void handleRollbackException(HttpServletResponse response, RollbackException e) throws IOException {
        LOG.error("ERROR", e);
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }


}


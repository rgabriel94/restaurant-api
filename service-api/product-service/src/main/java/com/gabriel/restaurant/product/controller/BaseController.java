package com.gabriel.restaurant.product.controller;

import com.gabriel.restaurant.product.exception.NotFoundException;
import com.gabriel.restaurant.product.exception.ResponseException;
import com.gabriel.restaurant.product.exception.EntityActionException;
import com.gabriel.restaurant.product.interfaces.BindingResultService;
import com.gabriel.restaurant.product.model.dto.error.ErrorResponseDTO;
import com.gabriel.restaurant.product.service.mapper.ModelMapperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
public abstract class BaseController {

    @Autowired
    protected BindingResultService bindingResultService;

    @Autowired
    protected ModelMapperService mapper;

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(ResponseException responseException) {
        log.warn(responseException.getMessage());
        return responseException.getResponse();
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(NotFoundException responseException) {
        log.warn(responseException.getMessage());
        return responseException.getResponse();
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(EntityActionException responseException) {
        log.error(responseException.getMessage());
        return responseException.getResponse();
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleException(Exception exception) {
        log.error(exception.getMessage());
        ResponseException responseException = new ResponseException(HttpStatus.BAD_REQUEST, "Bad request");
        return responseException.getResponse();
    }
}

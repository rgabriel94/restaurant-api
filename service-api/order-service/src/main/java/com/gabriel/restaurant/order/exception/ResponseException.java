package com.gabriel.restaurant.order.exception;

import com.gabriel.restaurant.order.model.dto.error.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseException extends RuntimeException {

    protected final HttpStatus httpStatus;
    protected final String response;

    public ResponseException(HttpStatus httpStatus, String response) {
        super(response);
        this.httpStatus = httpStatus;
        this.response = response;
    }

    public ResponseException(HttpStatus httpStatus, String response, Throwable throwable) {
        super(response, throwable);
        this.httpStatus = httpStatus;
        this.response = response;
    }

    public ResponseEntity<ErrorResponseDTO> getResponse() {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder().message(response).build();
        return ResponseEntity.status(httpStatus).body(errorResponseDTO);
    }
}

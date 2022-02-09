package com.gabriel.restaurant.order.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.restaurant.order.model.dto.error.ErrorResponseDTO;

public interface MapperForTest {

    default String mapperToJson(Object value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(value);
    }

    default String mapperListToJson(Object value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(value);
    }

    default ErrorResponseDTO errorResponseBuilder(String message) {
        return ErrorResponseDTO.builder()
                .message(message)
                .build();
    }
}

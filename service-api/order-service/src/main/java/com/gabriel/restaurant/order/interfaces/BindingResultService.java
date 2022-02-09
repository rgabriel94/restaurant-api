package com.gabriel.restaurant.order.interfaces;

import com.gabriel.restaurant.order.exception.ResponseException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface BindingResultService {

    void bindingResultErrors(BindingResult bindingResult) throws ResponseException;

    String createErrorMessage(List<ObjectError> objectErrors);
}

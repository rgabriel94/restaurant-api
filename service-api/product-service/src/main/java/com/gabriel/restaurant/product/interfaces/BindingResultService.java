package com.gabriel.restaurant.product.interfaces;

import com.gabriel.restaurant.product.exception.ResponseException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface BindingResultService {

    void bindingResultErrors(BindingResult bindingResult) throws ResponseException;

    String createErrorMessage(List<ObjectError> objectErrors);
}

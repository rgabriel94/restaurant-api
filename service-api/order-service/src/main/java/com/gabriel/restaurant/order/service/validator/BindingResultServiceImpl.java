package com.gabriel.restaurant.order.service.validator;

import com.gabriel.restaurant.order.exception.ResponseException;
import com.gabriel.restaurant.order.interfaces.BindingResultService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Service
public class BindingResultServiceImpl implements BindingResultService {

    @Override
    public void bindingResultErrors(BindingResult bindingResult) throws ResponseException {
        if (bindingResult.hasErrors()) {
            String errorMessage = createErrorMessage(bindingResult.getAllErrors());
            throw new ResponseException(HttpStatus.BAD_REQUEST, errorMessage);
        }
    }

    @Override
    public String createErrorMessage(List<ObjectError> objectErrors) {
        StringBuilder errorMessage = new StringBuilder();
        objectErrors.forEach(objectError -> errorMessage.append(objectError.getDefaultMessage()).append("\n"));
        errorMessage.delete(errorMessage.length() - 1, errorMessage.length());
        return errorMessage.toString();
    }
}

package com.devsuperior.dscatalog.services.exceptions;

import com.devsuperior.dscatalog.resources.exceptions.StandardError;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addErro(String fieldName, String message){
        errors.add(new FieldMessage(fieldName, message));
    }
}

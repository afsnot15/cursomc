package com.afonso.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Afonso
 */
public class ValidationError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError() {
    }

    public ValidationError(Integer status, String msg, Long timeStamp, String error, String path) {
        super(status, msg, timeStamp, error, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }
}

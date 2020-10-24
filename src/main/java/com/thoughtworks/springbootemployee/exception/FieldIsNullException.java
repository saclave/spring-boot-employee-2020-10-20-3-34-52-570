package com.thoughtworks.springbootemployee.exception;

public class FieldIsNullException extends RuntimeException{
    public static final String FIELD_IS_NULL = "Cannot accept null field [NAME]";

    public FieldIsNullException(String message) {
        super(message);
    }
}

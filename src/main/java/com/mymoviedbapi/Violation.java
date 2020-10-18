package com.mymoviedbapi;

public class Violation {
    private final String fieldName;
    private final String message;


    public String getMessage() {
        return message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}

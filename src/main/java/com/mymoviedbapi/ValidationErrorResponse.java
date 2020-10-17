package com.mymoviedbapi;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    public List<Violation> getViolations() {
        return violations;
    }

    private List<Violation> violations = new ArrayList<>();

}




package com.mymoviedbapi;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    public List<Violation> getViolations() {
        return violations;
    }

}




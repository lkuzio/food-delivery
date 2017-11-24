package xyz.javista.web.controlleradvice;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO extends ErrorDTO{

    private List<CustomFieldError> fieldErrors = new ArrayList<>();

    ValidationErrorDTO(int status, String message) {
        super(status,message);
    }

    public void addFieldError(String objectName, String path, String message) {
        CustomFieldError error = new CustomFieldError(objectName, path, message);
        fieldErrors.add(error);
    }

    public List<CustomFieldError> getFieldErrors() {
        return fieldErrors;
    }
}

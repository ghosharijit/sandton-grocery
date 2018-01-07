package za.co.santongrocery.api.exception;

import za.co.santongrocery.api.exception.Errors;

public class BusinessException extends Exception {
    private int errorCode;
    private String name;

    private BusinessException(int errorCode, String message, String name) {
        super(message);
        this.errorCode = errorCode;
        this.name = name;
    }

    public BusinessException(Errors errors, String message) {
        this(errors.getErrorCode(), errors.getDescription() + ": " + message, errors.name());
    }

    public BusinessException(Errors errors) {
        this(errors.getErrorCode(), errors.getDescription(), errors.name());
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getName() {
        return name;
    }
}

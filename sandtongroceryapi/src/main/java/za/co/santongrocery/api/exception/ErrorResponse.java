package za.co.santongrocery.api.exception;

import za.co.santongrocery.api.exception.BusinessException;
import za.co.santongrocery.api.exception.Errors;

public final class ErrorResponse {
    private int errorNumber;
    private String mnemonic;
    private String description;

    private ErrorResponse(String description, int errorNumber, String mnemonic) {
        this.description = description;
        this.errorNumber = errorNumber;
        this.mnemonic = mnemonic;
    }

    public static ErrorResponse from(BusinessException businessException) {
        return new ErrorResponse(businessException.getMessage(), businessException.getErrorCode(), businessException.getName());
    }

    public static ErrorResponse from(Exception exception) {
        return new ErrorResponse(exception.getMessage(), Errors.INTERNAL_ERROR.getErrorCode(), Errors.INTERNAL_ERROR.name());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }
}

package za.co.santongrocery.api.exception;

public enum Errors {

    INTERNAL_ERROR("Unexpected internal error"),
    TECHNICAL_ERROR("TECHNICAL ERROR"),
    JSON_ERROR("Incorrect JSON Message"),
    JSON_PROCESSING_ERROR("Error while convering Request Data into JSON"),
    INVALID_USER("Invalid User");

    private final String description;

    private Errors(String description) {
        this.description = description;
    }

    public int getErrorCode() {
        return this.ordinal();
    }

    public String getDescription() {
        return description;
    }
}

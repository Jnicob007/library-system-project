public class InvalidPublicationException extends Exception {

    public InvalidPublicationException(String message) {
        super(message);
    }

    public InvalidPublicationException(String message, Throwable cause) {
        super(message, cause);
    }

    // Static helper to validate publication data
    public static void validateString(String fieldName, String value) throws InvalidPublicationException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidPublicationException(fieldName + " cannot be empty or null.");
        }
    }

    public static void validatePositive(String fieldName, int value) throws InvalidPublicationException {
        if (value < 0) {
            throw new InvalidPublicationException(fieldName + " cannot be negative.");
        }
    }
}

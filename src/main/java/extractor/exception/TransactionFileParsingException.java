package extractor.exception;

public class TransactionFileParsingException extends ApplicationException {
    public TransactionFileParsingException() {
        super();
    }

    public TransactionFileParsingException(String message) {
        super(message);
    }

    public TransactionFileParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionFileParsingException(Throwable cause) {
        super(cause);
    }

    protected TransactionFileParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

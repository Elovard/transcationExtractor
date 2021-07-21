package extractor.exception;

public class ParserResolvingException extends ApplicationException {
    public ParserResolvingException() {
        super();
    }

    public ParserResolvingException(String message) {
        super(message);
    }

    public ParserResolvingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserResolvingException(Throwable cause) {
        super(cause);
    }

    protected ParserResolvingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package extractor.exception;

public class ExtensionResolvingException extends ApplicationException {
    public ExtensionResolvingException() {
        super();
    }

    public ExtensionResolvingException(String message) {
        super(message);
    }

    public ExtensionResolvingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtensionResolvingException(Throwable cause) {
        super(cause);
    }

    protected ExtensionResolvingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

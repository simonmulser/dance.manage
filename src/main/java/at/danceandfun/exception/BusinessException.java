package at.danceandfun.exception;

public class BusinessException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 545488786180671175L;

    public BusinessException(String message) {
        super(message);
    }

}

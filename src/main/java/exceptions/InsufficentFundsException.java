package exceptions;

public class InsufficentFundsException extends RuntimeException {

    public InsufficentFundsException(String message) {
        super(message);
    }
    public InsufficentFundsException() {
        this("Fundos Insuficientes!");
    }
}

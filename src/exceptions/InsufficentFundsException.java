package exceptions;

public class InsufficentFundsException extends Exception {

    public InsufficentFundsException(String message) {
        super(message);
    }
    public InsufficentFundsException() {
        this("Fundos Insuficientes!");
    }
}

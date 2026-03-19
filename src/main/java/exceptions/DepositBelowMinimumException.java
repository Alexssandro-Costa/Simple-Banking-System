package exceptions;

public class DepositBelowMinimumException extends RuntimeException {
    public DepositBelowMinimumException(String message) {
        super(message);
    }
    public  DepositBelowMinimumException() { this("O deposito informado está abaixo do minimo permitido.");}
}

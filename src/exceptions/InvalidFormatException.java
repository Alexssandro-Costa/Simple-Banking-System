package exceptions;

public class InvalidFormatException extends IllegalArgumentException {

    public InvalidFormatException(String message) {
        super(message);
    }
    public  InvalidFormatException() {
        super("O argumento passado tem um formato não invalido");
    }
}

package flagak.com.e_commerce.exception;

public class InvalidCredentialsException extends RuntimeException  {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}

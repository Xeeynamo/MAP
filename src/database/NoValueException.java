package database;

/**
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class NoValueException extends Exception {

    private String message;

    public NoValueException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

package database;

/**
 * Created by Luciano on 30/05/15.
 */
public class NoValueException extends Exception {

    private String message;

    public NoValueException(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return this.message;
    }
}

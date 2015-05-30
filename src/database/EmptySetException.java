package database;

/**
 * Created by Luciano on 30/05/15.
 */
public class EmptySetException extends Exception {

    private String message;

    public EmptySetException(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return this.message;
    }
}

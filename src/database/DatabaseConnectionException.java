package database;

/**
 * Created by Luciano on 30/05/15.
 */
public class DatabaseConnectionException extends Exception {
    private  String message;

    public DatabaseConnectionException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }
}

package database;

/**
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class DatabaseConnectionException extends Exception {
    private String message;

    public DatabaseConnectionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}

package data;

/**
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class EmptyDatasetException extends Exception {
    @Override
    public String toString() {
        return "DataSet is Empty!.\n";
    }
}
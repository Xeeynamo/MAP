package exception;

/**
 *
 * @author Windows 7
 */
public class EmptyDatasetException extends Exception
{
    @Override public String toString()
    {
        return "DataSet is Empty!.\n";
    }
}

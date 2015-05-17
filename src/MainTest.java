import data.Data;
import exception.ClusteringRadiusException;
import exception.EmptyDatasetException;
import mining.QTMiner;
import keyboard.Keyboard;

public class MainTest 
{

	/**
	 * @param args
	 */
	public static void main(String[] args) 
        {
            Data data =new Data();
            System.out.println(data);
            double radius;
            char risp;
            try
            {
                do
                {
                    try
                    {
                        do
                        {
                            System.out.println("Insert radius (=>0):");
                            radius=Keyboard.readDouble();
                        }while (radius<0.0);
                        QTMiner qt=new QTMiner(radius);
                        int numIter=qt.compute(data);
                        System.out.println("Number of clusters:"+numIter);
                        System.out.println(qt.getC().toString(data));
                    }
                    
                    catch(ClusteringRadiusException e)
                    {
                        System.out.println(e.toString());
                    }
                    
                    finally
                    {
                        do
                        {
                            System.out.println("New execution? (y/n):");
                            risp=Keyboard.readChar();
                        }while((risp!='y')&&(risp!='n'));
                    }


                }while(risp!='n');
            }
            
            catch(EmptyDatasetException e)
            {
                System.out.println(e.toString());
            }

	}

}
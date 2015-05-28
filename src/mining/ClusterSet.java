package mining;

import java.util.*;
import data.Data;

/**
 *
 * @author Windows 7
 */
public class ClusterSet implements Iterable<Cluster>, java.io.Serializable
{
    private Set<Cluster> C;
    
    ClusterSet()
    {
    	C = new TreeSet<Cluster>();
    }
    
    void add(Cluster c)
    {
        C.add(c);
    }
    
    public Iterator <Cluster> iterator ()
    {
    	return C.iterator();
    }
    
    public String toString ()
    {
        String x="";
        Iterator<Cluster> it=C.iterator();
        while(it.hasNext()) {
            x += it.next().toString();
            x += '\n';
        }
        return x;
    }
    
    public String toString(Data data )
    {
        String str="";
        Iterator<Cluster> it=C.iterator();
        for (int i=0;it.hasNext();i++)
        {
        	Cluster c=it.next();
        	if (c!=null)
        	{
        		str+=i+":"+c.toString(data)+"\n";
        	}
        }
        return str;
    }
}
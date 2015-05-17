package mining;

import data.Data;

/**
 *
 * @author Windows 7
 */
public class ClusterSet 
{
    private Cluster C[]=new Cluster[0];
    
    ClusterSet(){}
    
    void add(Cluster c)
    {
        Cluster tempC[]=new Cluster[this.C.length+1];
        for(int i=0;i<this.C.length;i++)
            tempC[i]=this.C[i];
        tempC[this.C.length]=c;
        this.C=tempC;
    }
    
    Cluster get(int i)
    {
        if (i<this.C.length)
            return this.C[i];
        else
            return null;
    }
    
    public String toString ()
    {
        String x="";
        for(int i=0;i<this.C.length;i++)
            x+=this.C[i].toString();
        return x;
    }
    
    public String toString(Data data )
    {
        String str="";
        for(int i=0;i<this.C.length;i++)
        {
            if (this.C[i]!=null)
            {
                str+=i+":"+this.C[i].toString(data)+"\n";
            }
        }
        return str;
    }
}
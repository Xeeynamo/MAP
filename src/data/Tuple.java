package data;

/**
 *
 * @author Windows 7
 */
public class Tuple 
{
    private Item [] tuple;
    
    Tuple (int size)
    {
        this.tuple= new Item [size];
    }
    
    public int getLenght()
    {
        return this.tuple.length;
    }
    
    public Item get (int i)
    {
        return this.tuple[i];
    }
    
    void add (Item c,int i)
    {
        if (i<this.tuple.length)
            this.tuple[i]=c;
    }
    
    public double getDistance (Tuple obj)
    {
        double x=0;
        for (int i=0;(i<this.getLenght())&&(i<obj.getLenght());i++)
            x+=this.get(i).distance((Object)obj.get(i));
        return x;
    }
    
    public double avgDistance(Data data, int clusteredData[])
    {
        double p=0.0,sumD=0.0;
        for(int i=0;i<clusteredData.length;i++)
        {
            double d= getDistance(data.getItemSet(clusteredData[i]));
            sumD+=d;
        }
        p=sumD/clusteredData.length;
        return p;
    }
}


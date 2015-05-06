/**
 * Created by Luciano on 06/05/15.
 */
public class Tuple {
    Item[] tuple;

    public Tuple(int size)
    {
        tuple = new Item[size];
    }

    public int getLength()
    {
        return tuple.length;
    }
    public Item get(int i)
    {
        return tuple[i];
    }
    public void add(Item c, int i)
    {
        tuple[i] = c;
    }
    public double getDistance(Tuple obj)
    {
        double distance = 0.0f;
        int n = Math.min(getLength(), obj.getLength());
        for (int i = 0; i < n; i++)
        {
            distance += get(i).distance(obj.get(i));
        }
        return distance;
    }
    public double avgDistance(Data data, int clusteredData[])
    {
        double sumD = 0.0;
        for (int i = 0; i < clusteredData.length; i++)
        {
            sumD += getDistance(data.getItemSet(clusteredData[i]));
        }
        return sumD / clusteredData.length;
    }
}

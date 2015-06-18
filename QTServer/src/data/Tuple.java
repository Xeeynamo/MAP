package data;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class Tuple implements java.io.Serializable {
    private Item[] tuple;

    Tuple(int size) {
        this.tuple = new Item[size];
    }

    public int getLenght() {
        return this.tuple.length;
    }

    public Item get(int i) {
        return this.tuple[i];
    }

    void add(Item c, int i) {
        if (i < this.tuple.length)
            this.tuple[i] = c;
    }

    public double getDistance(Tuple obj) {
        double x = 0;
        for (int i = 0; (i < this.getLenght()) && (i < obj.getLenght()); i++)
            x += this.get(i).distance((Object) obj.get(i));
        return x;
    }

    public double avgDistance(Data data, Set<Integer> clusteredData) {
        double p = 0.0, sumD = 0.0;
        for (Iterator<Integer> iterator = clusteredData.iterator(); iterator.hasNext(); ) {
            double d = getDistance(data.getItemSet(iterator.next()));
            sumD += d;
        }
        p = sumD / clusteredData.size();
        return p;
    }
}


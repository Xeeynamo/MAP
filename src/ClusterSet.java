/**
 * Created by Luciano on 06/05/15.
 */
public class ClusterSet extends Cluster {
    private Cluster C[] = new Cluster[0];

    public ClusterSet()
    {
        super();
    }
    public void add(Cluster c)
    {
        Cluster tempC[] = new Cluster[C.length + 1];
        for (int i = 0; i < C.length; i++)
            tempC[i] = C[i];
        tempC[C.length] = c;
        C = tempC;
    }
}

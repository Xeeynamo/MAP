/**
 * Created by Luciano on 06/05/15.
 */
public class QTMiner {
    ClusterSet C;
    double radius;

    public QTMiner(double radius)
    {
        this.radius = radius;
        C = new ClusterSet();
    }

    public ClusterSet getC()
    {
        return C;
    }
    public int compute(Data data)
    {
        int numclusters = 0;
        boolean isClustered[] = new boolean[data.getNumberOfExamples()];
        for (int i = 0; i < isClustered.length; i++)
            isClustered[i] = false;

        int countClustered = 0;
        while (countClustered != data.getNumberOfExamples())
        {
            // Ricerca del cluster più popoloso
            Cluster c = buildCandidateCluster(data, isClustered);
            C.add(c);

            // Rimuovo tuple clusterizzate da dataset
            int clusteredTupleId[] = c.iterator();
            for (int i = 0; i < clusteredTupleId.length; i++)
                isClustered[clusteredTupleId[i]] = true;
            countClustered += c.getSize();
        }
        return numclusters;
    }
    public Cluster buildCandidateCluster(Data data, boolean isClustered[])
    {
        return new Cluster();
    }
}

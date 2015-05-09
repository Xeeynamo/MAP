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
        Cluster cc=new Cluster(data.getItemSet(0)); //sarà il Candidato Cluster (nota: avrà size=0, quindi sarà sostituito dal primo //cluster rilevato, visto che quest'ultimo avrà ALMENO size=1, vedi if cc.getSize<c.getSize)
		
        for(int i=0;i<data.getNumberOfExamples();i++)
        {
			//costruisco un nuovo cluster, assicurandomi che non faccia parte di un altro cluster
            if (!isClustered[i])
            {
                //Costruisco il cluster che dovrà confrontarsi con il Candidato Cluster
                Cluster c=new Cluster (data.getItemSet(i));
                //Verifico quali/quante tuple facciano parte del cluster in analisi
                for(int j=0;j<data.getNumberOfExamples();j++)
                {
                    //Definisco la tupla alla j-esima posizione e anche per essa controllo che non faccia parte di un altro cluster
                    if(!isClustered[j])
                    {
						//se appartiene al cluster in analisi allora lo aggiungo
                        if(c.getCentroid().getDistance(data.getItemSet(j))<=this.radius)
                            c.addData(j);
                    }
                }
                //confronto il (finora) Candidato Cluster con il Cluster appena ottenuto
                //chi conterrà più tuple sarà il Candidato Cluster
                if(cc.getSize()<c.getSize())
                    cc=c;
            }
        }
		//ritorno il cluster più popolo (con più tuple) ritrovato
        return cc;
    }
}

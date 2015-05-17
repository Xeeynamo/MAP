package mining;

import data.Data;
import exception.ClusteringRadiusException;
import exception.EmptyDatasetException;

/**
 *
 * @author Windows 7
 */
public class QTMiner 
{
    private ClusterSet C;
    private double radius;
    
    public QTMiner (double radius)
    {
        C=new ClusterSet();
        this.radius=radius;
    }
    
    public ClusterSet getC()
    {
        return this.C;
    }
    
    
    private Cluster buildCandidateCluster(Data data, boolean isClustered[])
    {
        Cluster cc=new Cluster(data.getItemSet(0)); //sar� il Candidato Cluster
        for(int i=0;i<data.getNumberOfExamples();i++)
        {
            if (!isClustered[i])
            {
                //Costruisco il cluster che dovr� confrontarsi con il Candidato Cluster
                Cluster c=new Cluster (data.getItemSet(i));
                //Verifico quali/quante tuple facciano parte del cluster in analisi
                for(int j=0;j<data.getNumberOfExamples();j++)
                {
                    //Definisco la tupla alla j-esima posizione
                    if(!isClustered[j])
                    {
                        if(c.getCentroid().getDistance(data.getItemSet(j))<=this.radius)
                            c.addData(j);
                    }
                }
                //confronto il (finora) Candidato Cluster con il Cluster appena ottenuto
                //chi conterr� pi� tuple sar� a sua volta Candidato Cluster
                if(cc.getSize()<c.getSize())
                    cc=c;
            }
        }
        return cc;
    }
    
    /*
    Comportamento: Esegue l�algoritmo QT eseguendo i passi dello pseudo-codice:
        1. Costruisce un cluster per ciascuna tupla non ancora clusterizzata, includendo nel cluster i punti (non ancora clusterizzati in alcun altro cluster) che ricadano nel vicinato sferico della tuple avente raggio radius
        2. Salva il candidato cluster pi� popoloso e rimuove tutti punti di tale cluster dall'elenco delle tuple ancora da clusterizzare
        3. Ritorna al passo 1 finch� ci sono ancora tuple da assegnare ad un cluster
    */
    public int compute(Data data) throws ClusteringRadiusException,EmptyDatasetException
    {
        int numclusters=0;
        boolean isClustered[]=new boolean[data.getNumberOfExamples()];
        for(int i=0;i<isClustered.length;i++)
            isClustered[i]=false;
        int countClustered=0;
        while(countClustered!=data.getNumberOfExamples())
        {
            //Ricerca cluster pi� popoloso
            Cluster c=buildCandidateCluster(data, isClustered);
            if(c.getSize()==data.getNumberOfExamples())
                throw new ClusteringRadiusException(data.getNumberOfExamples());
            C.add(c);
            numclusters++;
            //Rimuovo tuple clusterizzate da dataset
            int clusteredTupleId[]=c.iterator();
            for(int i=0;i<clusteredTupleId.length;i++)
            {
                isClustered[clusteredTupleId[i]]=true;
            }
            countClustered+=c.getSize();
        }
        if (numclusters==0)
            throw new EmptyDatasetException();
        return numclusters;
    }
}
package mining;

/**
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class ClusteringRadiusException extends Exception {
    private int ntuples;

    public ClusteringRadiusException(int ntuples) {
        this.ntuples = ntuples;
    }

    @Override
    public String toString() {
        return ntuples + " tuples in one cluster!.\n";
    }
}
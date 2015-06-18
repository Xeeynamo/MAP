package data;

/**
 * Generico attributo
 *
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public abstract class Attribute implements java.io.Serializable {

    // Nome simbolico
    protected String name;
    // Identificativo numerico
    protected int index;

    /**
     * Inizializza un attributo
     *
     * @param name  nome dell'attributo
     * @param index identificativo dell'attributo
     */
    public Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Ottiene il nome dell'attributo
     *
     * @return nome dell'attributo
     */
    public String getName() {
        return name;
    }

    /**
     * Ottiene l'identificativo dell'attributo
     *
     * @return identificativo dell'attributo
     */
    public int getIndex() {
        return index;
    }

    /**
     * Stringa rappresentante lo stato dell'oggetto
     *
     * @return stato dell'oggetto
     */
    public String toString() {
        return getName();
    }
}

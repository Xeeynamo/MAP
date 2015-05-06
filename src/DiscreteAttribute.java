/**
 * Rappresentazione di un attributo contenente valori discreti
 * @author studente
 *
 */
public class DiscreteAttribute extends Attribute
{
	// per ogni stringa si ha un valore del dominio discreto.
	// I valori del dominio sono memorizzati in values seguendo
	// un ordine lessicografico
	private String values[];
	
	/**
	 * Inizializza
	 * @param name nome dell'attributo
	 * @param index identificativo dell'attributo
	 * @param values dominio dell'attributo
	 */
	public DiscreteAttribute(String name, int index, String values[])
	{
		super(name, index);
		this.values = values;
	}
	
	/**
	 * Restituisce la dimensione di values
	 * @return dimensione di values
	 */
	public int getNumberOfDistinctValues()
	{
		return values.length;
	}
	
	/**
	 * Restituisce values[i]
	 * @param indice di values
	 * @return stringa contenuta nell'indice i di values
	 */
	public String getValue(int i)
	{
		return values[i];
	}
}

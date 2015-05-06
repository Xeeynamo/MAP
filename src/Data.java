
public class Data {
	
	// matrice NxM dove ogni riga modella una transazione
	private Object data [][];
	// cardinalità dell'insieme di transazioni (numero di righe in data)
	private int numberOfExamples;
	// vettore degli attributi in ciascuna tupla (schema della tabella di dati)
	private Attribute attributeSet[];
	
	Data()
	{
		data = new Object [14][5];
		data[0][0] = "Sunny";
		data[0][1] = "Hot";
		data[0][2] = "High";
		data[0][3] = "Weak";
		data[0][4] = "No";
		data[1][0] = "Sunny";
		data[1][1] = "Hot";
		data[1][2] = "High";
		data[1][3] = "Strong";
		data[1][4] = "No";
		
		// numberOfExamples
		numberOfExamples = data.length;
		
		//explanatory Set
		attributeSet = new Attribute[5];

		String outLookValues[] = {"Sunny", "Overcast", "Rain"};
		attributeSet[0] = new DiscreteAttribute("Outlook", 0, outLookValues);
		
		String temperatureValues[] = {"Hot", "Mid", "Cold"};
		attributeSet[1] = new DiscreteAttribute("Temperature", 1, temperatureValues);
		
		String humidityValues[] = {"Hight", "Normal"};
		attributeSet[2] = new DiscreteAttribute("Humidity", 2, humidityValues);
		
		String windValues[] = {"Weak", "Strong"};
		attributeSet[3] = new DiscreteAttribute("Wind", 3, windValues);
		
		String playTennisValues[] = {"No", "Yes"};
		attributeSet[4] = new DiscreteAttribute("PlayTennis", 4, playTennisValues);
	}
	
	/**
	 * Restituisce il numero di explanatory set
	 * @return numero di explanatory set
	 */
	int getNumberOfExamples(){
		return numberOfExamples;
	}
	
	/**
	 * Restituisce il numero di attributi
	 * @return numero di attributi
	 */
	int getNumberOfAttributes(){
		return attributeSet.length;
	}
	
	/**
	 * Ottiene il valore di un dato
	 * @param exampleIndex indice di esempio
	 * @param attributeIndex numero dell'attributo
	 * @return valore restituito
	 */
	Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data[exampleIndex][attributeIndex];
	}
	
	Attribute getAttribute(int index){
		return attributeSet[index];
	}
	
	/**
	 * Crea una stringa in cui memorizza lo schema della tabella
	 * e le transazioni memorizzate in data, opportunamente
	 * enumerate. 
	 */
	public String toString()
	{
		String str = "";
		for (int i = 0; i < getNumberOfExamples(); i++)
		{
			str += i + ": ";
			for (int j = 0; j < getNumberOfAttributes(); i++)
			{
				str += getAttributeValue(i,  j);
			}
			str += "\n";
			
		}
		return str;
	}


	
	public static void main(String args[]){
		Data trainingSet=new Data();
		System.out.println(trainingSet);
		
		
	}

}

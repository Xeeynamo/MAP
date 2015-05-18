package data;
import java.util.*;

public class Data {

    // matrice NxM dove ogni riga modella una transazione
    private Object data[][];
    // cardinalità dell'insieme di transazioni (numero di righe in data)
    private int numberOfExamples;

    private List<Attribute> explanatorySet;


    public Data() {
		this.data=new Object [14][5];
		//Outlook
		this.data[0][0]="Sunny";
		this.data[1][0]="Sunny";
		this.data[2][0]="Overcast";
		this.data[3][0]="Rain";
		this.data[4][0]="Rain";
		this.data[5][0]="Rain";
		this.data[6][0]="Overcast";
		this.data[7][0]="Sunny";
		this.data[8][0]="Sunny";
		this.data[9][0]="Rain";
		this.data[10][0]="Sunny";
		this.data[11][0]="Overcast";
		this.data[12][0]="Overcast";
		this.data[13][0]="Rain";
		//Temperature
		this.data[0][1]="Hot";
		this.data[1][1]="Hot";	
		this.data[2][1]="Hot";	
		this.data[3][1]="Mild";	
		this.data[4][1]="Cool";	
		this.data[5][1]="Cool";	
		this.data[6][1]="Cool";	
		this.data[7][1]="Mild";	
		this.data[8][1]="Cool";	
		this.data[9][1]="Mild";	
		this.data[10][1]="Mild";	
		this.data[11][1]="Mild";	
		this.data[12][1]="Hot";	
		this.data[13][1]="Mild";
		//Humidity
		this.data[0][2]="High";
		this.data[1][2]="High";
		this.data[2][2]="High";
		this.data[3][2]="High";
		this.data[4][2]="Normal";
		this.data[5][2]="Normal";
		this.data[6][2]="Normal";
		this.data[7][2]="High";
		this.data[8][2]="Normal";
		this.data[9][2]="Normal";
		this.data[10][2]="Normal";
		this.data[11][2]="High";
		this.data[12][2]="Normal";
		this.data[13][2]="High";
		//Wind
		this.data[0][3]="Weak";
		this.data[1][3]="Strong";
		this.data[2][3]="Weak";
		this.data[3][3]="Weak";
		this.data[4][3]="Weak";
		this.data[5][3]="Strong";
		this.data[6][3]="Strong";
		this.data[7][3]="Weak";
		this.data[8][3]="Weak";
		this.data[9][3]="Weak";
		this.data[10][3]="Strong";
		this.data[11][3]="Strong";
		this.data[12][3]="Weak";
		this.data[13][3]="Strong";
		//PlayTennis
		this.data[0][4]="No";
		this.data[1][4]="No";
		this.data[2][4]="Yes";
		this.data[3][4]="Yes";
		this.data[4][4]="Yes";
		this.data[5][4]="No";
		this.data[6][4]="Yes";
		this.data[7][4]="No";
		this.data[8][4]="Yes";
		this.data[9][4]="Yes";
		this.data[10][4]="Yes";
		this.data[11][4]="Yes";
		this.data[12][4]="Yes";
		this.data[13][4]="No";
		
		this.numberOfExamples=14;
		explanatorySet = new LinkedList<>();
		//Outlook
		String OutlookValues[]=new String[3];
		OutlookValues[0]="Sunny";
		OutlookValues[1]="Rain";
		OutlookValues[2]="Overcast";
		explanatorySet.add(new DiscreteAttribute("outlook",0,OutlookValues));
		//Temperature
		String TemperatureValues[]=new String[3];
		TemperatureValues[0]="Hot";
		TemperatureValues[1]="Mild";
		TemperatureValues[2]="Cool";
		explanatorySet.add(new DiscreteAttribute("temperature",1,TemperatureValues));
		//Humidity
		String HumidityValues[]=new String[2];
		HumidityValues[0]="Normal";
		HumidityValues[1]="Hot";
		explanatorySet.add(new DiscreteAttribute("humidity",2,HumidityValues));
		//Wind
		String WindValues[]=new String[2];
		WindValues[0]="Weak";
		WindValues[1]="Strong";
		explanatorySet.add(new DiscreteAttribute("wind",3,WindValues));
		//PlayTennis
		String PlayTennisValues[]=new String[2];
		PlayTennisValues[0]="No";
		PlayTennisValues[1]="Yes";
		explanatorySet.add(new DiscreteAttribute("wind",4,WindValues));
    }

    /**
     * Restituisce il numero di explanatory set
     *
     * @return numero di explanatory set
     */
    public int getNumberOfExamples() {
        return numberOfExamples;
    }

    
    /**
     * Restituisce il valore di una particolare tupla e di un particolare attributo nel data-set
     * 
     * @param exampleIndex indice della tupla
     * @param attributeIndex indice dell'attributo
     * @return valore indivuato nella tupla di indice <exampleIndex> con attributo di indice
     * <attributeIndex> 
     */
	public Object getValue(int exampleIndex,int attributeIndex)
	{
		return data[exampleIndex][attributeIndex];
	}

    /**
     * Ottiene il valore di un dato
     *
     * @param exampleIndex   indice di esempio
     * @param attributeIndex numero dell'attributo
     * @return valore restituito
     */
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
        return data[exampleIndex][attributeIndex];
    }

    /**
     * Ritorna la tupla che corrisponde all'indice passato in parametro
     * @param index indice dell'attributo
     * @return atupla riferito dall'indice
     */
    
	public Attribute getAttribute(int index) {
        return explanatorySet.get(index);
    }

    /**
     * Ottiene il numero di attribut
     * @return numero di attributi
     */
	public int getNumberOfExplanatoryAttributes() {
        return explanatorySet.size();
    }


    public List<Attribute> getAttributeSchema()
    {
        return this.explanatorySet;
    }
    

    /**
     * Crea una stringa in cui memorizza lo schema della tabella
     * e le transazioni memorizzate in data, opportunamente
     * enumerate.
     */
	public String toString()
	{
		String value="";
		for(int i=0;i<numberOfExamples;i++)
                {
			value+=(i+1)+":";
			for(int j = 0; j < getNumberOfExplanatoryAttributes() -1 ; j++)
				value+=this.getValue(i, j)+",";
			value+=this.getValue(i, getNumberOfExplanatoryAttributes()-1)+"\n";
		}
		return value;
	}

    public Tuple getItemSet(int index)
    {
        Tuple tuple=new Tuple(getNumberOfExplanatoryAttributes());
        for(int i=0;i<getNumberOfExplanatoryAttributes();i++)
            tuple.add(new DiscreteItem((DiscreteAttribute)getAttribute(i),(String)data[index][i]),i);
        return tuple;
    }


    public static void main(String args[]) {
        Data trainingSet = new Data();
        System.out.println(trainingSet);


    }

}

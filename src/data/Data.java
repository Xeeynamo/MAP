package data;

import database.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class Data {

    // matrice NxM dove ogni riga modella una transazione
    private List<Example> data;
    // cardinalità dell'insieme di transazioni (numero di righe in data)
    private int numberOfExamples;

    private List<Attribute> explanatorySet;


    public Data(String table) throws EmptySetException {
        this.data = new ArrayList<Example>();
        DbAccess db = new DbAccess();
        TableData td = new TableData(db);

        try {
            db.initConnection();
            this.data = td.getDistinctTransazioni(table);
        } catch (DatabaseConnectionException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


        //Outlook
        /*this.data[0][0]="Sunny";
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
		data[0][1]=new Double (30.3);
		data[1][1]=new Double (30.3);
		data[2][1]=new Double (30);
		data[3][1]=new Double (13);
		data[4][1]=new Double (0);
		data[5][1]=new Double (0);
		data[6][1]=new Double (0.1);
		data[7][1]=new Double (13);
		data[8][1]=new Double (0.1);
		data[9][1]=new Double (12);
		data[10][1]=new Double (12.5);
		data[11][1]=new Double (12.5);
		data[12][1]=new Double(29.21);
		data[13][1]=new Double (12.5);
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
		this.data[13][4]="No";*/

        this.numberOfExamples = 14;
        explanatorySet = new LinkedList<>();
        //Outlook
        String OutlookValues[] = new String[3];
        OutlookValues[0] = "Sunny";
        OutlookValues[1] = "Rain";
        OutlookValues[2] = "Overcast";
        explanatorySet.add(new DiscreteAttribute("outlook", 0, OutlookValues));
        //Temperature
        explanatorySet.add(new ContinuousAttribute("Temperature", 1, 3.2, 38.7));
        //Humidity
        String HumidityValues[] = new String[2];
        HumidityValues[0] = "Normal";
        HumidityValues[1] = "Hot";
        explanatorySet.add(new DiscreteAttribute("humidity", 2, HumidityValues));
        //Wind
        String WindValues[] = new String[2];
        WindValues[0] = "Weak";
        WindValues[1] = "Strong";
        explanatorySet.add(new DiscreteAttribute("wind", 3, WindValues));
        //PlayTennis
        String PlayTennisValues[] = new String[2];
        PlayTennisValues[0] = "No";
        PlayTennisValues[1] = "Yes";
        explanatorySet.add(new DiscreteAttribute("wind", 4, WindValues));
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
     * Ottiene il valore di un dato
     *
     * @param exampleIndex   indice di esempio
     * @param attributeIndex numero dell'attributo
     * @return valore restituito
     */
    public Object getAttributeValue(int exampleIndex, int attributeIndex) {
        return data.get(exampleIndex).get(attributeIndex);
    }

    /**
     * Ritorna la tupla che corrisponde all'indice passato in parametro
     *
     * @param index indice dell'attributo
     * @return atupla riferito dall'indice
     */

    public Attribute getAttribute(int index) {
        return explanatorySet.get(index);
    }

    /**
     * Ottiene il numero di attribut
     *
     * @return numero di attributi
     */
    public int getNumberOfExplanatoryAttributes() {
        return explanatorySet.size();
    }


    public List<Attribute> getAttributeSchema() {
        return this.explanatorySet;
    }


    /**
     * Crea una stringa in cui memorizza lo schema della tabella
     * e le transazioni memorizzate in data, opportunamente
     * enumerate.
     */
    public String toString() {
        String value = "";
        for (int i = 0; i < numberOfExamples; i++) {
            value += (i + 1) + ":";
            for (int j = 0; j < getNumberOfExplanatoryAttributes() - 1; j++)
                value += this.getAttributeValue(i, j) + ",";
            value += this.getAttributeValue(i, getNumberOfExplanatoryAttributes() - 1) + "\n";
        }
        return value;
    }

    public Tuple getItemSet(int index) {
        Tuple tuple = new Tuple(getNumberOfExplanatoryAttributes());
        for (int i = 0; i < getNumberOfExplanatoryAttributes(); i++) {
            if (DiscreteAttribute.class.isInstance(getAttribute(i)))
                tuple.add(new DiscreteItem((DiscreteAttribute) getAttribute(i), (String) data.get(index).get(i)), i);
            else
                tuple.add(new ContinuousItem((ContinuousAttribute) getAttribute(i), (double) data.get(index).get(i)), i);
        }
        return tuple;
    }
}

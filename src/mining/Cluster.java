package mining;

import java.util.*;
import data.Data;
import data.Tuple;

public class Cluster implements Iterable<Integer>, Comparable<Cluster>
{
	private Tuple centroid;

	//private ArraySet clusteredData;
	private Set<Integer> clusteredData;

	/*Cluster(){
		
	}*/

	public Cluster(Tuple centroid){
		this.centroid=centroid;
		clusteredData = new HashSet<>();
		
	}
		
	Tuple getCentroid(){
		return centroid;
	}
	
	//return true if the tuple is changing cluster
	boolean addData(int id){
		return clusteredData.add(id);
		
	}
	
	//verifica se una transazione è clusterizzata nell'array corrente
	boolean contain(int id){
		return clusteredData.contains(id);
	}
	

	//remove the tuplethat has changed the cluster
	void removeTuple(int id){
		clusteredData.remove(id);
		
	}
	
	int  getSize(){
		return clusteredData.size();
	}
	
	public int compareTo(Cluster cluster)
	{
		return (clusteredData.size() != cluster.getSize()) == true ? 1 : -1;
	}

	public Iterator<Integer> iterator()
	{
		return clusteredData.iterator();
	}
	
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLenght();i++)
			str+=centroid.get(i);
		str+=")";
		return str;
		
	}
	
	


	
	public String toString(Data data){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLenght();i++)
			str+=centroid.get(i)+ " ";
		str+=")\nExamples:\n";
		Iterator<Integer> array = iterator();
		/*for(int i = 0; i < getSize(); i++)
		{
			str+="[";
			for(int j=0;j<data.getNumberOfExplanatoryAttributes();j++)
				str+=data.getValue(array[i], j)+" ";
			str+="] dist="+getCentroid().getDistance(data.getItemSet(array[i]))+"\n";
		}*/
		HashSet<Integer> set = new HashSet<Integer>();
		for (Iterator<Integer> i = iterator(); i.hasNext();)
		{
			Integer v = i.next();
			set.add(v);
			str+="[";
			for(int j=0;j<data.getNumberOfExplanatoryAttributes();j++)
				str+=data.getValue(v, j)+" ";
			str+="] dist="+getCentroid().getDistance(data.getItemSet(v))+"\n";
		}

		str+="\nAvgDistance="+getCentroid().avgDistance(data, set);
		return str;
		
	}

}
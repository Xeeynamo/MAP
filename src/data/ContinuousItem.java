package data;


public class ContinuousItem extends Item
{
	ContinuousItem(ContinuousAttribute attribute, Double value)
	{	
		super(attribute,value);
	}
	
	public double distance (Object a)
	{
		ContinuousItem ci=(ContinuousItem)a;
		ContinuousAttribute ca=(ContinuousAttribute)this.attribute;
		return Math.abs(ca.getScaledValue((double)this.value)-ca.getScaledValue((double)ci.getValue()));
	}
}

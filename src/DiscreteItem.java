/**
 * Created by Luciano on 06/05/15.
 */
public class DiscreteItem extends Item {

    public DiscreteItem(DiscreteAttribute attribute, String value)
    {
        super(attribute, value);
    }
    public double distance(Object a)
    {
        return getValue().equals(a) == true ? 0 : 1;
    }
}

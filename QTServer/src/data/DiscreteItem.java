package data;

/**
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class DiscreteItem extends Item {

    public DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }

    public double distance(Object a) {
        return this.equals(a) == true ? 0 : 1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getValue() == null) ? 0 : this.getValue().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DiscreteItem other = (DiscreteItem) obj;
        if (this.getAttribute().getName().compareTo(other.getAttribute().getName()) != 0)
            return false;
        if (this.getValue() == null) {
            if (other.getValue() != null)
                return false;
        } else if (!this.getValue().equals(other.getValue()))
            return false;
        return true;
    }
}

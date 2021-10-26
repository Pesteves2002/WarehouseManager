package ggc;

import java.util.Comparator;


public class BatchComparator implements Comparator<Batch> {

    private static final long serialVersionUID = 202110262231L;


    CollatorWrapper c = new CollatorWrapper();

    @Override
    public int compare(Batch _this, Batch other) {
        if (c.compare(_this.getThisProductID(), other.getThisProductID()) == 0) {
            if (c.compare(_this.get_partner(), other.get_partner()) == 0) {
                if (_this.getPrice() - other.getPrice() == 0)
                    return _this.getQuantity() - other.getQuantity();
                return (int) _this.getPrice() - (int) other.getPrice();
            }
            return c.compare(_this.get_partner(), other.get_partner());
        }
        return c.compare(_this.getThisProductID(), other.getThisProductID());
    }


}

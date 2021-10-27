package ggc;

import java.util.Comparator;

/**
 * Class that implements a comparator to the class Batch
 */
public class BatchComparator implements Comparator<Batch> {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262231L;
  /** Create a Wrapper that sorts by alphabetical order */
  CollatorWrapper c = new CollatorWrapper();

  /**
   * Compare two batches
   * @param _this
   * @param other
   * @return int
   */
  @Override
  public int compare(Batch _this, Batch other) {
    if (c.compare(_this.getThisProductID(), other.getThisProductID()) == 0) {
      if (c.compare(_this.get_partner(), other.get_partner()) == 0) {
        if (_this.getPrice() - other.getPrice() == 0)
          return _this.getStock() - other.getStock();
        return (int) _this.getPrice() - (int) other.getPrice();
      }
      return c.compare(_this.get_partner(), other.get_partner());
    }
    return c.compare(_this.getThisProductID(), other.getThisProductID());
  }


}

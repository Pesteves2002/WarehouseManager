package ggc;

public class New extends Notification{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262229L;

  public New(String productKey, double productPrice) {
    super(productKey, productPrice);
  }

  @Override
  public String toString() {
    return "NEW" + "|" + super.toString();
  }
}

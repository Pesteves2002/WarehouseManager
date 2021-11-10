package ggc;

public class Bargain extends Notification{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262229L;

  public Bargain(String productKey, double productPrice) {
    super(productKey, productPrice);
  }

  @Override
  public String toString() {
    return "BARGAIN" + "|" + super.toString();
  }
}

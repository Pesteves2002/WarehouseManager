package ggc;

public class Acquisition extends Transaction{

  /**
   * Class serial number.
   */
  private static final long serialVersionUID = 202110262235L;

  public Acquisition(int transactionKey, int transactionDate, String partnerKey, String productKey, int amount, double baseValue) {
    super(transactionKey, transactionDate, partnerKey, productKey, amount, baseValue);
  }

  @Override
  public String toString() {
    return "COMPRA" +  "|" + super.toString();
  }
}

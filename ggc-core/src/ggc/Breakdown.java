package ggc;

public class Breakdown extends Transaction {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  private String components;

  public double paymentValue ;

  public Breakdown(int transactionKey, int transactionDate, String partnerKey, String productKey, int amount, double baseValue, String components) {
    super(transactionKey, transactionDate, partnerKey, productKey, amount, baseValue);
    this.components = components;
    this.paymentValue = baseValue;
  }

  public double getPaymentValue() {
    return paymentValue;
  }

  public String getComponents() {
    return components;
  }

  @Override
  public String accept(TransactionVisitor tv) {
    return tv.visitBreakdown(this);
  }


}

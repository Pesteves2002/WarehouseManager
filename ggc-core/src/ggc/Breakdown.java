package ggc;

public class Breakdown extends Transaction {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  private String components;

  public double paymentValue;

  public Breakdown(int transactionKey, int transactionDate, Partner partner, String productKey, int amount, double baseValue, String components) {
    super(transactionKey, transactionDate, partner, productKey, amount, baseValue);
    this.components = components;
    this.paymentValue = baseValue;
  }

  public String getComponents() {
    return components;
  }

  public double getPaymentValue() {
    return paymentValue;
  }

  @Override
  public String accept(TransactionVisitor tv) {
    return tv.visitBreakdown(this);
  }

  @Override
  public double seePrice(TransactionVisitor tv, int time) {
    return tv.getPriceBreakDown(this, time);
  }

  public double paySale( ShowSale tv, int warehouseDate) {return tv.paySale(this, warehouseDate);}

}

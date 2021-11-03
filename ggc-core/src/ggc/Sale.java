package ggc;

public class Sale extends Transaction{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  public double currentValue ;

  public int deadLine;

  public int paymentDate  = 0;


  public Sale(int transactionKey, int transactionDate, String partnerKey, String productKey, int amount, double baseValue , int deadLine) {
    super(transactionKey, transactionDate, partnerKey, productKey, amount, baseValue);
    this.deadLine = deadLine;
    this.currentValue = baseValue;
  }

  public double getCurrentValue() {
    return currentValue;
  }

  public int getDeadLine() {
    return deadLine;
  }

  @Override
  public int getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(int paymentDate) {
    this.paymentDate = paymentDate;
  }

  public String accept (TransactionVisitor tv)
  {
    return tv.visitSale(this);
  }
}

package ggc;

public class Sale extends Transaction{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  private double currentValue ;

  private int deadLine;

  private int paymentDate  = 0;

  private boolean salePayed;


  public Sale(int transactionKey, int transactionDate, String partnerKey, String productKey, int amount, double baseValue , int deadLine , boolean salePayed) {
    super(transactionKey, transactionDate, partnerKey, productKey, amount, baseValue);
    this.deadLine = deadLine;
    this.currentValue = baseValue;
    this.salePayed = salePayed;
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

  public boolean isSalePayed() {
    return salePayed;
  }

  public String accept (TransactionVisitor tv)
  {
    return tv.visitSale(this);
  }
}

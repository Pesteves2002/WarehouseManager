package ggc;

public class Sale extends Transaction{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  private double currentValue ;

  private int deadLine;

  private int paymentDate  = 0;

  private boolean salePayed ;

  private boolean derivedProduct;


  public Sale(int transactionKey, int transactionDate, Partner partner, String productKey, int amount, double baseValue , int deadLine , boolean salePayed , boolean derivedProduct) {
    super(transactionKey, transactionDate, partner, productKey, amount, baseValue);
    this.deadLine = deadLine;
    this.currentValue = baseValue;
    this.salePayed = salePayed;
    this.derivedProduct = derivedProduct;
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

  public void setPaymentDate(int paymentDate, double value) {
    this.paymentDate = paymentDate;
    this.salePayed = true;
    this.currentValue =value;
  }

  public boolean isSalePayed() {
    return salePayed;
  }

  public boolean isDerivedProduct() {
    return derivedProduct;
  }

  public String accept (TransactionVisitor tv)
  {
    return tv.visitSale(this);
  }

  public double seePrice (TransactionVisitor tv, int time)
  {
    return tv.getPriceSale(this,time);
  }

  public double paySale( ShowSale tv, int warehouseDate) {return tv.paySale(this, warehouseDate);}
}

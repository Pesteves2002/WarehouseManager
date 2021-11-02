package ggc;

public class Sale extends Transaction{

  public Sale(int transactionKey, int transactionDate, String partnerKey, String productKey, int amount, double baseValue) {
    super(transactionKey, transactionDate, partnerKey, productKey, amount, baseValue);
  }

  public String accept (TransactionVisitor tv)
  {
    return tv.visitSale(this);
  }
}

package ggc;

public class Breakdown extends Transaction{

  @Override
  public String accept(TransactionVisitor tv) {
    return tv.visitBreakdown(this);
  }

  public Breakdown(int transactionKey, int transactionDate, String partnerKey, String productKey, int amount, double baseValue) {
    super(transactionKey, transactionDate, partnerKey, productKey, amount, baseValue);


  }
}

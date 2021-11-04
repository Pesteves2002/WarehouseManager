package ggc;

public class Breakdown extends Transaction {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  public Breakdown(int transactionKey, int transactionDate, String partnerKey, String productKey, int amount, double baseValue) {
    super(transactionKey, transactionDate, partnerKey, productKey, amount, baseValue);
  }

  @Override
  public String accept(TransactionVisitor tv) {
    return tv.visitBreakdown(this);
  }


}

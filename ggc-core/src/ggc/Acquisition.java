package ggc;

public class Acquisition extends Transaction{

  /**
   * Class serial number.
   */
  private static final long serialVersionUID = 202110262235L;

  public Acquisition(int transactionKey, int transactionDate, Partner partner, String productKey, int amount, double baseValue) {
    super(transactionKey, transactionDate, partner, productKey, amount, baseValue);
  }

  @Override
  public String accept(TransactionVisitor tv) {
    return tv.visitAcquisition(this);
  }

  @Override
  public double seePrice(TransactionVisitor tv, int time) {
    return tv.getPriceAcquisition(this, time);
  }

  public double paySale( ShowSale tv, int warehouseDate) {return tv.paySale(this, warehouseDate);}
}

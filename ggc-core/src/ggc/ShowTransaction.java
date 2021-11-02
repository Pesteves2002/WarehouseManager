package ggc;

public class ShowTransaction implements TransactionVisitor {

  @Override
  public String visitAcquisition(Acquisition acquisition) {
    return acquisition.getTransactionKey() + "|" +
            acquisition.getPartnerKey() + "|" +
            acquisition.getProductKey() + "|" +
            acquisition.getAmount() + "|" +
            (int) acquisition.getBaseValue() + "|" +
            acquisition.getPaymentDate();
  }

  @Override
  public String visitSale(Sale sale) {
    return null;
  }

  @Override
  public String visitBreakdown(Breakdown breakdown) {
    return null;
  }
}

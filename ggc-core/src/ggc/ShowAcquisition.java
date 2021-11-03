package ggc;

public class ShowAcquisition implements TransactionVisitor {

  @Override
  public String visitAcquisition(Acquisition acquisition) {
    return "COMPRA" + "|"+
            acquisition.getTransactionKey() + "|" +
            acquisition.getPartnerKey() + "|" +
            acquisition.getProductKey() + "|" +
            acquisition.getAmount() + "|" +
            (int) acquisition.getBaseValue() + "|" +
            acquisition.getPaymentDate();
  }

  @Override
  public String visitSale(Sale sale) {return "";}

  @Override
  public String visitBreakdown(Breakdown breakdown) {
    return "";
  }
}

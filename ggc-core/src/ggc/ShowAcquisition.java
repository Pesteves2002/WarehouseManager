package ggc;

public class ShowAcquisition extends TransactionVisitor {

  @Override
  public String visitAcquisition(Acquisition acquisition) {
    return "COMPRA" + "|"+
            acquisition.getTransactionKey() + "|" +
            acquisition.getPartner().getPartnerKey() + "|" +
            acquisition.getProductKey() + "|" +
            acquisition.getAmount() + "|" +
            (int) Math.round(acquisition.getBaseValue()) + "|" +
            acquisition.getPaymentDate();
  }

  @Override
  public String visitSale(Sale sale) {return "";}

  @Override
  public String visitBreakdown(Breakdown breakdown) {
    return "";
  }



}

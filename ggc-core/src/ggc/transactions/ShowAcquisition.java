package ggc.transactions;

public class ShowAcquisition extends TransactionVisitor {

  @Override
  public String visitAcquisition(Acquisition acquisition, int time) {
    return "COMPRA" + "|"+
            acquisition.getTransactionKey() + "|" +
            acquisition.getPartner().getPartnerKey() + "|" +
            acquisition.getProductKey() + "|" +
            acquisition.getAmount() + "|" +
            (int) Math.round(acquisition.getBaseValue()) + "|" +
            acquisition.getPaymentDate();
  }

  @Override
  public String visitSale(Sale sale, int time) {return "";}

  @Override
  public String visitBreakdown(Breakdown breakdown, int time) {
    return "";
  }



}

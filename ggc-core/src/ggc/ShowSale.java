package ggc;

public class ShowSale extends TransactionVisitor{

  @Override
  public String visitAcquisition(Acquisition acquisition) {
    return "";
  }

  @Override
  public String visitSale(Sale sale) {
    if (!sale.isSalePayed()) {
      return "";
    }
    return "VENDA" + "|" +
            sale.getTransactionKey() + "|" +
            sale.getPartner().getPartnerKey() + "|" +
            sale.getProductKey() + "|" +
            sale.getAmount() + "|" +
            (int) sale.getBaseValue() + "|" +
            (int) sale.getCurrentValue() + "|" +
            sale.getDeadLine() + "|" + sale.getPaymentDate();
  }

  @Override
  public String visitBreakdown(Breakdown breakdown) {
    return "";
  }

}

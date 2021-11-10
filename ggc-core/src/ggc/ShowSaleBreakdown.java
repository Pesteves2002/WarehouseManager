package ggc;

public class ShowSaleBreakdown extends TransactionVisitor {

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
            (int) Math.round(sale.getBaseValue()) + "|" +
            (int) Math.round(sale.getCurrentValue()) + "|" +
            sale.getDeadLine() + "|" + sale.getPaymentDate();
  }


  @Override
  public String visitBreakdown(Breakdown breakdown) {
    return "DESAGREGAÇÃO" + "|" +
            breakdown.getTransactionKey() + "|" +
            breakdown.getPartner().getPartnerKey() + "|" +
            breakdown.getProductKey() + "|" +
            breakdown.getAmount() + "|" +
            (int) Math.round(breakdown.getBaseValue()) + "|" +
            (int) Math.round(breakdown.getPaymentValue()) + "|" +
            breakdown.getPaymentDate() + "|" +
            breakdown.getComponents();

  }


}

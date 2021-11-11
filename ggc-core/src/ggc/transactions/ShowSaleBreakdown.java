package ggc.transactions;

public class ShowSaleBreakdown extends TransactionVisitor {

  @Override
  public String visitAcquisition(Acquisition acquisition, int time) {
    return "";
  }

  @Override
  public String visitSale(Sale sale, int time) {
    String s = "";
    if (sale.isSalePayed()) {
      s = "|" + sale.getPaymentDate();
    }

    return "VENDA" + "|" +
            sale.getTransactionKey() + "|" +
            sale.getPartner().getPartnerKey() + "|" +
            sale.getProductKey() + "|" +
            sale.getAmount() + "|" +
            (int) Math.round(sale.getBaseValue()) + "|" +
            (int)  Math.round(sale.getCurrentValue()* (1+ sale.seePrice(new ShowSale(),time))) + "|" +
            sale.getDeadLine() + s;
  }


  @Override
  public String visitBreakdown(Breakdown breakdown, int time) {
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

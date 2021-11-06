package ggc;

public class ShowTransaction implements TransactionVisitor {

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
  public String visitSale(Sale sale) {
    String s = "VENDA" + "|"+
            sale.getTransactionKey() + "|" +
            sale.getPartnerKey() + "|" +
            sale.getProductKey() + "|" +
            sale.getAmount() + "|" +
            (int) sale.getBaseValue() + "|" +
            (int) sale.getCurrentValue()+ "|" +
            sale.getDeadLine() ;
    if (sale.isSalePayed())
      return  s + "|" + sale.getPaymentDate();
    return s;
  }

  @Override
  public String visitBreakdown(Breakdown breakdown) {

    return "DESAGREGAÇÃO" + "|"+
            breakdown.getTransactionKey() + "|" +
            breakdown.getPartnerKey() + "|" +
            breakdown.getProductKey() + "|" +
            breakdown.getAmount() + "|" +
            (int) breakdown.getBaseValue() + "|" +
            breakdown.getPaymentValue()+ "|" +
            breakdown.getPaymentDate() + "|" +
             breakdown.getComponents();

  }
}

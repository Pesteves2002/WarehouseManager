package ggc;

public class ShowTransaction extends TransactionVisitor {

  @Override
  public String visitAcquisition(Acquisition acquisition) {
    return "COMPRA" + "|"+
            acquisition.getTransactionKey() + "|" +
            acquisition.getPartner().getPartnerKey() + "|" +
            acquisition.getProductKey() + "|" +
            acquisition.getAmount() + "|" +
            (int) acquisition.getBaseValue() + "|" +
            acquisition.getPaymentDate();
  }

  @Override
  public String visitSale(Sale sale) {
    String s = "VENDA" + "|"+
            sale.getTransactionKey() + "|" +
            sale.getPartner().getPartnerKey() + "|" +
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
            breakdown.getPartner().getPartnerKey() + "|" +
            breakdown.getProductKey() + "|" +
            breakdown.getAmount() + "|" +
            (int) breakdown.getBaseValue() + "|" +
            breakdown.getPaymentValue()+ "|" +
            breakdown.getPaymentDate() + "|" +
             breakdown.getComponents();

  }

  @Override
  public double getPriceAcquisition(Acquisition acquisition, int time) {
    return acquisition.getBaseValue();
  }

  @Override
  public double getPriceSale(Sale sale, int time) {
    Partner partner = sale.getPartner();

    return partner.pay(time, sale.isDerivedProduct(),(int) sale.getBaseValue(),true);
  }

  @Override
  public double getPriceBreakDown(Breakdown breakdown, int time) {
    return breakdown.getPaymentValue() ;
  }
}

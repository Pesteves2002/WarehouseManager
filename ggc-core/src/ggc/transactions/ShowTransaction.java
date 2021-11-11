package ggc.transactions;

import ggc.partners.Partner;

public class ShowTransaction extends TransactionVisitor {

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
  public String visitSale(Sale sale, int time) {
    String s = "VENDA" + "|"+
            sale.getTransactionKey() + "|" +
            sale.getPartner().getPartnerKey() + "|" +
            sale.getProductKey() + "|" +
            sale.getAmount() + "|" +
            (int) Math.round(sale.getBaseValue()) + "|" +
            (int)  Math.round(sale.getCurrentValue()* (1+ sale.seePrice(new ShowSale(),time)))  + "|" +
            sale.getDeadLine() ;
    if (sale.isSalePayed())
      return  s + "|" + sale.getPaymentDate();
    return s;
  }

  @Override
  public String visitBreakdown(Breakdown breakdown, int time) {

    return "DESAGREGAÇÃO" + "|"+
            breakdown.getTransactionKey() + "|" +
            breakdown.getPartner().getPartnerKey() + "|" +
            breakdown.getProductKey() + "|" +
            breakdown.getAmount() + "|" +
            (int) Math.round(breakdown.getBaseValue()) + "|" +
            (int) Math.round(breakdown.getPaymentValue()) + "|" +
            breakdown.getPaymentDate() + "|" +
             breakdown.getComponents();

  }

  @Override
  public double getPriceAcquisition(Acquisition acquisition, int time) {
    return  - acquisition.getBaseValue();
  }

  @Override
  public double getPriceSale(Sale sale, int time) {
    Partner partner = sale.getPartner();
    time = sale.getDeadLine() - time;
    if (sale.isSalePayed()) return sale.getCurrentValue();
    return sale.getBaseValue()* (1 + partner.pay(time, sale.isDerivedProduct(),(int) sale.getBaseValue(),true));
  }

  @Override
  public double getPriceBreakDown(Breakdown breakdown, int time) {
    return 0 ;
  }
}

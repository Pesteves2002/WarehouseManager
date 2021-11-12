package ggc.transactions;

import ggc.partners.Partner;

public class ShowSale extends TransactionVisitor {

  @Override
  public String visitAcquisition(Acquisition acquisition, int time) {
    return "";
  }

  @Override
  public String visitSale(Sale sale, int time) {
    if (!sale.isSalePayed()) {
      return "";
    }
    return "VENDA" + "|" +
            sale.getTransactionKey() + "|" +
            sale.getPartner().getPartnerKey() + "|" +
            sale.getProductKey() + "|" +
            sale.getAmount() + "|" +
            (int) Math.round(sale.getBaseValue()) + "|" +
            (int)  Math.round(sale.seePrice(new ShowSale(),time)) + "|" +
            sale.getDeadLine() + "|" + sale.getPaymentDate();
  }

  @Override
  public String visitBreakdown(Breakdown breakdown, int time) {
    return "";
  }

  public double paySale(Acquisition acquisition,int warehouseDate) {
    return 0;
  }

  public double paySale(Sale sale, int warehouseDate) {
    if (sale.isSalePayed()) return 0;
    Partner partner = sale.getPartner();
    // positive if on time, negative otherwise
    int differenceOfDays = sale.getDeadLine() - warehouseDate;
    double partnerBonus = partner.pay(differenceOfDays, sale.isDerivedProduct(), (int) sale.getBaseValue(), false);
    double value = sale.getBaseValue() * (1 + partnerBonus);
    sale.setPaymentDate(warehouseDate, value);
    return value;
  }

  public double paySale(Breakdown breakdown,  int warehouseDate) {
    return 0;
  }

  @Override
  public double getPriceAcquisition(Acquisition acquisition, int time) {
    return  0;
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

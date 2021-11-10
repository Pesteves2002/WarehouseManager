package ggc;

public class ShowSale extends TransactionVisitor {

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
    return "";
  }

  public double paySale(Acquisition acquisition,int warehouseDate) {
    return 0;
  }

  public double paySale(Sale sale, int warehouseDate) {
    Partner partner = sale.getPartner();
    int differenceOfDays = sale.getDeadLine() - warehouseDate;
    double partnerBonus = partner.pay(differenceOfDays, sale.isDerivedProduct(), (int) sale.getBaseValue(), false);
    double value = sale.getBaseValue() * (1 + partnerBonus);
    sale.setPaymentDate(warehouseDate, value);
    return value;
  }

  public double paySale(Breakdown breakdown,  int warehouseDate) {
    return 0;
  }


}

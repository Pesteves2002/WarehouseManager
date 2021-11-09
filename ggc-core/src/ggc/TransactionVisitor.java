package ggc;

public abstract class TransactionVisitor {
  public  String visitAcquisition (Acquisition acquisition) {return "";}

  public  String visitSale (Sale sale){return "";}

  public   String visitBreakdown (Breakdown breakdown){return "";}

  public double getPriceAcquisition(Acquisition acquisition, int time) {return 0;}

public double getPriceSale (Sale sale, int time) {return 0;}

public double getPriceBreakDown (Breakdown breakdown, int time){return 0;}
}

package ggc;

public interface TransactionVisitor {
  public String visitAcquisition (Acquisition acquisition);

  public String visitSale (Sale sale);

  public String visitBreakdown (Breakdown breakdown);
}

package ggc;


public class Elite extends Status {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262231L;

  public Elite(Partner partner) {
    super(partner);
  }

  @Override
  public void eliteToSelection() {partner.setStatus(new Selection(partner));  }

  /** String representation of the Elite Status */
  public String toString() {
    return "ELITE";
  }
}
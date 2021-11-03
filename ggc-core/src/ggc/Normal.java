package ggc;


public class Normal extends Status {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262230L;

  public Normal(Partner partner) {
    super(partner);
  }

  public void normalToSelection() {partner.setStatus(new Selection(partner));}

  /** String representation of the Normal Status */
  public String toString() {
    return "NORMAL";
  }
}
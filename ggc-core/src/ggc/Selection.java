package ggc;


public class Selection extends Status {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  public Selection(Partner partner) {
    super(partner);
  }

  @Override
  public void selectionToElite() {
    partner.setStatus(new Elite(partner));
  }

  @Override
  public void selectionToNormal() {
    partner.setStatus(new Normal(partner));
  }

  /** String representation of the Selection Status */
  public String toString() {
    return "SELECTION";
  }
}
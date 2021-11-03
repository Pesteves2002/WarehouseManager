package ggc;

import java.io.Serializable;


/**
 * Abstract Class that is related to the Partner
 */
public abstract class Status implements Serializable {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262227L;

  protected Partner partner;

  public Status(Partner partner) {
    this.partner = partner;
  }

  public void normalToSelection() {}
  public void selectionToElite() {}
  public void eliteToSelection() {}
  public void selectionToNormal() {}

}

package ggc.partners;

import java.io.Serializable;


/**
 * Abstract Class that is related to the Partner
 */
public abstract class Status implements Serializable {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262227L;

  protected Partner partner;

  protected double points;

  public Status(Partner partner, double points) {
    this.partner = partner;
    this.points = points;
  }

  public double getPoints() {
    return points;
  }

  public void addPoints(double points)
  {
    this.points += points;
  }

  public abstract double p1(int baseValue, boolean simulate) ;
  public abstract double p2(int baseValue, int differenceOfDays,boolean simulate) ;
  public abstract double p3(int baseValue, int differenceOfDays,boolean simulate) ;
  public abstract double p4(int baseValue, int differenceOfDays,boolean simulate) ;

}

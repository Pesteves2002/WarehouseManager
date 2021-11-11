package ggc.partners;


public class Normal extends Status {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262230L;

  public Normal(Partner partner, double points) {
    super(partner, points);
  }

  public void normalToSelection() {
    partner.setStatus(new Selection(partner, points));
  }

  public double p1(int baseValue, boolean simulate) {
    if (simulate) return -0.1;

    points += baseValue * 10;
    if (points > 2000) {

      if (points > 10000) {
        partner.setStatus(new Elite(partner, points));
      } else
        partner.setStatus(new Selection(partner, points));
    }
    return -0.1;
  }

  public double p2(int baseValue, int differenceOfDays, boolean simulate) {
    if (simulate) return 0;

    points += baseValue * 10;
    if (points > 2000) {

      if (points > 10000) {
        partner.setStatus(new Elite(partner, points));
      } else
        partner.setStatus(new Selection(partner, points));
    }
    return 0;
  }

  public double p3(int baseValue, int differenceOfDays, boolean simulate) {
    if (simulate) return differenceOfDays * 0.05;
    points = 0;
    return differenceOfDays * 0.05;
  }

  public double p4(int baseValue, int differenceOfDays, boolean simulate) {
    if (simulate) return differenceOfDays * 0.1;

    points = 0;
    return differenceOfDays * 0.1;
  }

  /** String representation of the Normal Status */
  public String toString() {
    return "NORMAL";
  }
}
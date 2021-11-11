package ggc.partners;


public class Selection extends Status {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  public Selection(Partner partner, double point) {
    super(partner, point);
  }


  public void selectionToElite() {
    partner.setStatus(new Elite(partner, points));
  }


  public void selectionToNormal() {
    partner.setStatus(new Normal(partner, points));
  }

  public double p1(int baseValue, boolean simulate) {
    if (simulate) return -0.1;
    this.points += baseValue * 10 * (0.9);
    if (points > 25000) {
      selectionToElite();
    }
    return -0.1;
  }

  public double p2(int baseValue, int differenceOfDays, boolean simulate) {
    if (simulate) {
      if (differenceOfDays >= 2)
        return -0.05;
      return 0;
    }

    if (points > 25000) {
      selectionToElite();
    }
    if (differenceOfDays >= 2) {
      this.points += baseValue * 10 * 0.95;
      return -0.05;
    }
    this.points += baseValue * 10;
    return 0;
  }

  public double p3(int baseValue, int differenceOfDays, boolean simulate) {
    if (differenceOfDays > 1)
      return differenceOfDays * 0.02;
    return 0;
  }

  public double p4(int baseValue, int differenceOfDays, boolean simulate) {
    if (simulate) return 0.05 * differenceOfDays;
    if (differenceOfDays > 2) {
      this.points *= 0.1;
      selectionToNormal();

    }
    return 0.05 * differenceOfDays;
  }

  /** String representation of the Selection Status */
  public String toString() {
    return "SELECTION";
  }
}
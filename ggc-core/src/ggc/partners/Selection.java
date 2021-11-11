package ggc.partners;


public class Selection extends Status {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  public Selection(Partner partner, double points) {
    super(partner, points);
  }


  public void selectionToElite() {
    partner.setStatus(new Elite(partner, points));
  }


  public void selectionToNormal() {
    partner.setStatus(new Normal(partner, points));
  }

  public double p1 (int baseValue, boolean simulate)
  {
    if (simulate) return -0.1;
    points += baseValue *10;
    if (points > 25000)
    {
      selectionToElite();
    }
    return -0.1;
  }

  public double p2 (int baseValue, int differenceOfDays, boolean simulate)
  {
    if (simulate) {
      if (differenceOfDays >= 2)
        return -0.05;
      return 0;
    }

    points += baseValue *10;
    if (points > 25000)
    {
      selectionToElite();
    }
    if (differenceOfDays >= 2)
      return 0.05;
    return 0;
  }

  public double p3(int baseValue, int differenceOfDays, boolean simulate) {
    if (- differenceOfDays > 1 )
      return differenceOfDays * 0.02;
    return 0;
  }

  public double p4(int baseValue, int differenceOfDays, boolean simulate)
  {
    if (simulate)  return  0.05* differenceOfDays;
    if (differenceOfDays > 2) {
      selectionToNormal();
      points *= 0.1;
    }
    return  0.05 * differenceOfDays;
  }

  /** String representation of the Selection Status */
  public String toString() {
    return "SELECTION";
  }
}
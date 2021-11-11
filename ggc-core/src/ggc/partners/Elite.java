package ggc.partners;


public class Elite extends Status {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262231L;

  public Elite(Partner partner , int points) {
    super(partner , points);
  }

  public void eliteToSelection() {partner.setStatus(new Selection(partner, points));  }

  public double p1 (int baseValue, boolean simulate)
  {
    if (simulate) return -0.1;
    points += baseValue *10;
    return -0.1;
  }

  public double p2 (int baseValue, int differenceOfDays, boolean simulate)
  {
    if (simulate) return -0.1;
    points += baseValue *10;
    return -0.1;
  }

  public double p3(int baseValue, int differenceOfDays, boolean simulate) {
    return 0.05;
  }

  public double p4(int baseValue, int differenceOfDays, boolean simulate)
  {
    if (simulate) return 0;
    if (differenceOfDays > 15) {
      eliteToSelection();
      points *= 0.25;
    }
    return  0;
  }


  /** String representation of the Elite Status */
  public String toString() {
    return "ELITE";
  }
}
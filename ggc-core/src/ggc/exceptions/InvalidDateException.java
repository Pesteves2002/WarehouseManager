package ggc.exceptions;

/**
 * Exception for Invalid Date
 */
public class InvalidDateException extends Exception {
  /**
   * Class serial number.
   */
  private static final long serialVersionUID = 202110262231L;
  /**
   * Bad time to advance.
   */
  private int timeToAdvance;

  /**
   * @param timeToAdvance
   */
  public InvalidDateException(int timeToAdvance) {
    this.timeToAdvance = timeToAdvance;
  }

  /**
   * @return the bad time
   */
  public int getTimeToAdvance() {
    return timeToAdvance;
  }
}
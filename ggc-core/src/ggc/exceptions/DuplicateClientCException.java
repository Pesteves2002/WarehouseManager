package ggc.exceptions;

/**
 * Exception for trying to create a new Partner with the same ID
 */

public class DuplicateClientCException extends Exception {
  /**
   * Class serial number
   */
  private static final long serialVersionUID = 202110262234L;

  /**
   * Value of the ID
   */
  private String duplicateID;

  /**
   * * @param duplicateID
   */
  public DuplicateClientCException(String duplicateID) {
    this.duplicateID = duplicateID;
  }

  /**
   * @return the duplicate ID
   */
  public String get_duplicateID() {
    return duplicateID;
  }
}

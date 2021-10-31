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
   * Value of the Key
   */
  private String duplicateKey;

  /**
   * * @param duplicateKey
   */
  public DuplicateClientCException(String duplicateKey) {
    this.duplicateKey = duplicateKey;
  }

  /**
   * @return the duplicate ID
   */
  public String getDuplicateKey() {
    return duplicateKey;
  }
}

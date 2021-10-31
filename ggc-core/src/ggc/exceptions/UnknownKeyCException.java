package ggc.exceptions;

/**
 * Exception for unknown PartnerKey
 */
public class UnknownKeyCException extends Exception {
  /**
   * Class serial number.
   */
  private static final long serialVersionUID = 202110262235L;
  /**
   * bad entry specification
   */
  private String unknownKey;

  /**
   * @param unknownKey
   */
  public UnknownKeyCException(String unknownKey) {
    this.unknownKey = unknownKey;
  }

  public String getUnknownKey() {
    return unknownKey;
  }
}

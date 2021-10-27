package ggc.exceptions;

/**
 * Exception for unknown PartnerKey
 */
public class UnknownPartnerKeyCException extends Exception {
  /**
   * Class serial number.
   */
  private static final long serialVersionUID = 202110262235L;
  /**
   * bad entry specification
   */
  private String unknownPartnerKey;

  /**
   * @param unknownPartnerKey
   */
  public UnknownPartnerKeyCException(String unknownPartnerKey) {
    this.unknownPartnerKey = unknownPartnerKey;
  }

  public String getUnknownPartnerKey() {
    return unknownPartnerKey;
  }
}

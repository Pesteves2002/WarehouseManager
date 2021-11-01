package ggc.exceptions;

public class UnknownPartnerKeyCException extends UnknownKeyCException{

  /**
   * Class serial number.
   */
  private static final long serialVersionUID = 202110262235L;

  public UnknownPartnerKeyCException(String unknownKey) {
    super(unknownKey);
  }
}

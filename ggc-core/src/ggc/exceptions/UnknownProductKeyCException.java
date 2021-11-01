package ggc.exceptions;

public class UnknownProductKeyCException extends UnknownKeyCException {

  /**
   * Class serial number.
   */
  private static final long serialVersionUID = 202110262235L;

  public UnknownProductKeyCException(String unknownKey) {
    super(unknownKey);
  }
}

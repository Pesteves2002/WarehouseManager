package ggc.exceptions;

public class UnknownTransactionKeyCException extends UnknownKeyCException{

  /**
   * Class serial number.
   */
  private static final long serialVersionUID = 202110262235L;

  public UnknownTransactionKeyCException(String unknownKey) {
    super(unknownKey);
  }
}

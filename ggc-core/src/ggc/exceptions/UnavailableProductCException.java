package ggc.exceptions;

public class UnavailableProductCException extends Exception{

  /**
   * Class serial number.
   */
  private static final long serialVersionUID = 202110262235L;
  /**
   * bad entry specification
   */
  private String productKey;

  private int amountRequested;

  private int amountAvailable;


  public UnavailableProductCException(String productKey, int amountRequested, int amountAvailable) {
    this.productKey = productKey;
    this.amountRequested = amountRequested;
    this.amountAvailable = amountAvailable;
  }

  public String getProductKey() {
    return productKey;
  }

  public int getAmountRequested() {
    return amountRequested;
  }

  public int getAmountAvailable() {
    return amountAvailable;
  }
}

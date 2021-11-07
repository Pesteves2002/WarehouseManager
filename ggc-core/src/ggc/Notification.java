package ggc;

import java.io.Serializable;

public abstract class Notification implements Serializable {

  private static final long serialVersionUID = 202110271150L;

  private String productKey;

  private int productPrice;

  public Notification(String productKey, int productPrice) {
    this.productKey = productKey;
    this.productPrice = productPrice;
  }

  @Override
  public String toString() {
    return productKey + "|" + productPrice;
  }
}
package ggc.notifications;

import java.io.Serializable;

public abstract class Notification implements Serializable {

  private static final long serialVersionUID = 202110271150L;

  private String productKey;

  private double productPrice;

  public Notification(String productKey, double productPrice) {
    this.productKey = productKey;
    this.productPrice = productPrice;
  }

  @Override
  public String toString() {
    return productKey + "|" + (int) Math.round(productPrice);
  }
}
package ggc;

import java.io.Serializable;

/**
 * Class that holds a Product, its price, its quantity, the reduction and the partner
 */
public class Batch implements Serializable {
  /** Serial number for serialization */
  private static final long serialVersionUID = 202110262232L;

  /** ProductID */
  private String _product;

  /** Price of the Product */
  private float Price;

  /** Stock of the Batch */
  private int stock;

  /** Reduction (only applicable to Derived Products */
  private float reduction = 0;

  /** PartnerID */
  private String _partner;

  /**
   * Simple Product
   *
   * @param thisProduct
   * @param price
   * @param quantity
   * @param partner
   */
  public Batch(String thisProduct, float price, int quantity, String partner) {
    _product = thisProduct;
    Price = price;
    stock = quantity;
    _partner = partner;

  }

  /**
   * Derived Product
   *
   * @param thisProduct
   * @param price
   * @param quantity
   * @param partner
   * @param reduction
   */
  public Batch(String thisProduct, float price, int quantity, String partner, float reduction) {
    this(thisProduct, price, quantity, partner);
    this.reduction = reduction;

  }

  /**
   * @return ProductID
   */
  public String getThisProductID() {
    return _product;
  }

  /**
   * @return Price
   */
  public float getPrice() {
    return Price;
  }

  /**
   * @return Stock
   */
  public int getStock() {
    return stock;
  }

  /**
   * @return the partnerID
   */
  public String get_partner() {
    return _partner;
  }

  /**
   * String representation of the batch: presents the productID, the partnerID, the price and the stock of the batch
   *
   * @return a string representation of the Batch.
   */
  @Override
  public String toString() {
    return _product + "|" + _partner + "|" + Math.round(Price) + "|" + stock;
  }
}
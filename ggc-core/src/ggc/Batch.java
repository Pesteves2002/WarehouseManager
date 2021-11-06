package ggc;

import java.io.Serializable;

/**
 * Class that holds a Product, its price, its quantity, the reduction and the partner
 */
public class Batch implements Serializable, Comparable<Batch> {
  /** Serial number for serialization */
  private static final long serialVersionUID = 202110262232L;

  /** ProductID */
  private String product;

  /** Price of the Product */
  private double price;

  /** Stock of the Batch */
  private int stock;

  /** Reduction (only applicable to Derived Products */
  private double reduction = 0;

  /** PartnerID */
  private String partner;

  /**
   * Simple Product
   *
   * @param product
   * @param price
   * @param stock
   * @param partner
   */
  public Batch(String product, double price, int stock, String partner) {
    this.product = product;
    this.price = price;
    this.stock = stock;
    this.partner = partner;

  }

  /**
   * Derived Product
   *
   * @param product
   * @param price
   * @param stock
   * @param partner
   * @param reduction
   */
  public Batch(String product, double price, int stock, String partner, double reduction) {
    this(product, price, stock, partner);
    this.reduction = reduction;
  }

  /**
   * @return ProductID
   */
  public String getThisProductID() {
    return product;
  }

  /**
   * @return Price
   */
  public double getPrice() {
    return price;
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
    return partner;
  }

  public void decreaseStock(int amount) {
    stock -= amount;
  }

  public int emptyStock() {
    int aux = (int) (stock*price);
    stock = 0;
    return aux;
  }

  /**
   * String representation of the batch: presents the productID, the partnerID, the price and the stock of the batch
   *
   * @return a string representation of the Batch.
   */
  @Override
  public String toString() {
    return product + "|" + partner + "|" + Math.round(price) + "|" + stock;
  }

  public int compareTo (Batch other)
  {
    CollatorWrapper collator = new CollatorWrapper();
    if (collator.compare(this.getThisProductID(), other.getThisProductID()) == 0) {
      if (collator.compare(this.get_partner(), other.get_partner()) == 0) {
        if (this.getPrice() - other.getPrice() == 0)
          return this.getStock() - other.getStock();
        return (int) this.getPrice() - (int) other.getPrice();
      }
      return collator.compare(this.get_partner(), other.get_partner());
    }
    return collator.compare(this.getThisProductID(), other.getThisProductID());
  }
}
package ggc;

import java.io.Serializable;
import java.util.*;

/**
 * Class that represents the product that can be stored in a Batch
 * It has a price, and a Stock
 */
public class  Product implements Serializable {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  /** Product Name */
  private String productKey;

  /** Maximum price on this product */
  private double maxPrice;

  /** Total stock of the product */
  private int actualStock;

  /**
   * All the batches that have the product
   */
  private TreeSet<Batch> batches = new TreeSet<Batch>(new BatchComparator());

  /**
   * @param productKey
   * @param maxPrice
   * @param actualStock
   */
  public Product(String productKey, double maxPrice, int actualStock) {

    this.productKey = productKey;
    this.maxPrice = maxPrice;
    this.actualStock = actualStock;
  }

  public String getProductKey() {
    return productKey;
  }



  /**
   * Return the batch of a product all sorted
   *
   * @return Tree<Batch>
   */
  public TreeSet<Batch> get_batches() {
    return batches;
  }

  /**
   * Adds stock when a new batch is created
   *
   * @param newStock
   */

  public void addStock(int newStock) {
    actualStock += newStock;
  }

  /**
   * If a new batch has a bigger price for a product, the
   * max price will change
   *
   * @param value
   */
  public void changeMaxPrice(double value) {
    if (maxPrice < value)
      maxPrice = value;
  }

  /**
   * Add the batch to the Map
   *
   * @param newBatch
   */
  public void addBatch(Batch newBatch) {
    batches.add(newBatch);
  }


  /**
   * String Representation of the Product class
   *
   * @return String
   */
  @Override
  public String toString() {
    return productKey + "|" + Math.round(maxPrice) + "|" + actualStock;
  }


}
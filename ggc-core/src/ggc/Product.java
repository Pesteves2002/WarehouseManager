package ggc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

/**
 * Class that represents the product that can be stored in a Batch
 * It has a price, and a Stock
 */
public class Product implements Serializable {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  /** Product Name */
  private String productKey;

  /** Maximum price on this product */
  private float maxPrice;

  /** Total stock of the product */
  private int actualStock;

  /**
   * All the batches that have the product
   */
  private List<Batch> batches = new ArrayList<Batch>();

  /**
   * @param productKey
   * @param maxPrice
   * @param actualStock
   */
  public Product(String productKey, float maxPrice, int actualStock) {

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
   * @return List<Batch>
   */
  public List<Batch> get_batches() {

    batches.sort(new BatchComparator());
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
  public void changeMaxPrice(float value) {
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
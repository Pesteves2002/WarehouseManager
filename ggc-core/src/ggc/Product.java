package ggc;

import java.io.Serializable;
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
  private String productName;

  /** Max price on the this product */
  private float maxPrice;

  /** Total stock of the product */
  private int actualStock;

  /**
   * All the batches that have the product
   */
  private List<Batch> _batches = new LinkedList<Batch>();

  /**
   * @param productName
   * @param maxPrice
   * @param actualStock
   */
  public Product(String productName, float maxPrice, int actualStock) {

    this.productName = productName;
    this.maxPrice = maxPrice;
    this.actualStock = actualStock;
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
    _batches.add(newBatch);
  }

  /**
   * Return the batch of a product all sorted
   *
   * @return List<Batch>
   */
  public List<Batch> get_batches() {

    _batches.sort(new BatchComparator());
    return _batches;
  }

  /**
   * String Represenation of the Product class
   *
   * @return String
   */
  @Override
  public String toString() {
    return productName + "|" + Math.round(maxPrice) + "|" + actualStock;
  }


}
package ggc.products;

import ggc.notifications.Bargain;
import ggc.notifications.New;
import ggc.notifications.Notification;
import ggc.notifications.Observer;
import ggc.notifications.Subject;

import java.io.Serializable;
import java.util.*;

/**
 * Class that represents the product that can be stored in a Batch
 * It has a price, and a Stock
 */
public class Product implements Serializable, Subject {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262228L;

  /** Product Name */
  private String productKey;

  /** Maximum price on this product */
  private double maxPrice;

  /** Total stock of the product */
  private int actualStock;

  private double minPrice;

  private ArrayList<ggc.notifications.Observer> observers = new ArrayList<>();

  /**
   * All the batches that have the product
   */
  private LinkedList<Batch> batches = new LinkedList<>();

  /**
   * @param productKey
   * @param price
   * @param actualStock
   */
  public Product(String productKey, double price, int actualStock) {

    this.productKey = productKey;
    this.maxPrice = 0;
    this.actualStock = actualStock;
    this.minPrice = 9999999;
  }

  public String getProductKey() {
    return productKey;
  }

  public double getMaxPrice() {
    return maxPrice;
  }

  /**
   * Return the batch of a product all sorted
   *
   * @return Tree<Batch>
   */
  public LinkedList<Batch> get_batches() {
    return batches;
  }

  public int getActualStock() {
    return actualStock;
  }

  public void setActualStock(int actualStock) {
    this.actualStock = actualStock;
  }

  public double clearAllStock() {
    int price = 0;
    for (Batch batch : batches) {
      price += batch.emptyStock();
      batches.remove(batch);
    }

    this.setActualStock(0);
    return price;
  }

  public void setMaxPrice(double maxPrice) {
    this.maxPrice = maxPrice;
  }

  /**
   * Adds stock when a new batch is created
   *
   * @param newStock
   */

  public void addStock(int newStock) {
    if (actualStock == 0) {
      int price = (int) this.maxPrice;
      notifyObservers(new New(this.productKey, price));
    }
    actualStock += newStock;
  }

  public void reduceStock(int amount) {
    this.actualStock -= amount;

  }


  /**
   * If a new batch has a bigger price for a product, the
   * max price will change
   *
   * @param value
   */
  public void changeMinMaxPrice(double value) {
    if (maxPrice < value)
      maxPrice = value;
    if (minPrice > value)
      minPrice = value;
  }


  /**
   * Add the batch to the Map
   *
   * @param newBatch
   */
  public void addBatch(Batch newBatch) {


    if (newBatch.getPrice() < minPrice && !batches.isEmpty()) {
      notifyObservers(new Bargain(this.productKey, (int) newBatch.getPrice()));
    }
    batches.add(newBatch);
    batches.sort(new BatchComparator());
    changeMinMaxPrice(newBatch.getPrice());

  }

  public void registerObserver(ggc.notifications.Observer o) {
    observers.add(o);
  }

  public void removeObserver(ggc.notifications.Observer o) {
    int i = observers.indexOf(o);
    if (i >= 0) {
      observers.remove(i);
    }
  }

  public void notifyObservers(Notification notification) {
    for (Observer observer : observers) {
      observer.update(notification);
    }
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
package ggc;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents the owner of some batches,
 * it can buy or sell some products
 */
public class Partner implements Serializable, Observer {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262229L;

  /** partnerKey */
  private String partnerKey;

  /** PartnerName */
  private String partnerName;

  /** PartnerAddress */
  private String partnerAddress;

  /** Partner Status */
  private Status status = new Normal(this, 0);

  /** Money spent on purchases by the partner */
  private double moneySpentOnPurchases;

  /** money expect to spend on sales by the partner */
  private double moneyExpectedToSpendOnSales;

  /** money spent on Sales by the partner */
  private double moneySpentOnSales;

  private List<Transaction> transactionList = new LinkedList<>();

  private List<Notification> notificationList = new LinkedList<>();

  private List<Batch> thisBatches = new LinkedList<>();

  private Map<String, Product> thisNotifications = new TreeMap<>();

  private Delivery delivery = new DefaultNotification();

  /**
   * @param partnerKey
   * @param partnerName
   * @param partnerAddress
   */
  public Partner(String partnerKey, String partnerName, String partnerAddress) {
    this.partnerKey = partnerKey;
    this.partnerName = partnerName;
    this.partnerAddress = partnerAddress;
  }

  /**
   * Return the partnerID
   *
   * @return partnerID
   */
  public String getPartnerKey() {
    return partnerKey;
  }


  public List<Batch> getThisBatches() {
    thisBatches.sort(new BatchComparator());
    return thisBatches;
  }

  public List<Transaction> getTransactionList() {
    return transactionList;
  }


  public void setMoneyExpectedToSpendOnSales(double moneyExpectedToSpendOnSales) {
    this.moneyExpectedToSpendOnSales = moneyExpectedToSpendOnSales;
  }

  protected void setStatus(Status status) {
    this.status = status;
  }


  public void addProductNotification(Product product) {
    String productKey = product.getProductKey();
    thisNotifications.put(productKey, product);
    thisNotifications.get(productKey).registerObserver(this);
  }

  public void addMoneySpentOnSales(double moneySpentOnSales) {
    this.moneySpentOnSales += moneySpentOnSales;
  }

  public void addMoneySpentOnPurchases(double moneySpentOnPurchases) {
    this.moneySpentOnPurchases += moneySpentOnPurchases;
  }

  public void addTransaction(Transaction transaction) {
    transactionList.add(transaction);
  }

  /**
   * Add a batch to the Tree Map
   *
   * @param batch
   */
  public void addBatch(String productKey, Batch batch) {
    thisBatches.add(batch);
  }


  public void addPoints(int points)
  {
    status.addPoints(points);
  }

  public void toggleProductNotification(Product product) {

    if (thisNotifications.get(product.getProductKey()) == null) {
      addProductNotification(product);
    }
    thisNotifications.remove(product.getProductKey());
  }

  public void update(Notification notification) {
    notificationList.add(notification);
  }

  public String deliver() {
    String output = this + delivery.showAndClearNotifications(notificationList);
    if (!notificationList.isEmpty())
      notificationList.clear();
    return output;
  }

  public void changeDelivery(Delivery delivery)
  {
    this.delivery = delivery;
  }

  public void removeBatch(Batch batch) {
    thisBatches.remove(batch);
  }


  public double pay(int differenceOfDays, boolean productDerived, int baseValue, boolean simulate) {
    int numberOfDays = 5;
    if (productDerived) {
      numberOfDays = 3;
    }
    if (differenceOfDays >= 0) {
      if (differenceOfDays >= numberOfDays)
        return status.p1(baseValue, simulate);
      return status.p2(baseValue, differenceOfDays, simulate);
    }
    if (-differenceOfDays <= numberOfDays)
      return status.p3(baseValue, -differenceOfDays, simulate);

    return status.p4(baseValue, -differenceOfDays, simulate);
  }

  /**
   * String representation of the Class Partner
   *
   * @return
   */
  public String toString() {

    return partnerKey + "|" + partnerName + "|" + partnerAddress + "|" + status + "|" +
            status.getPoints() + "|" + (int) Math.round(moneySpentOnPurchases) + "|" +
            (int) Math.round(moneyExpectedToSpendOnSales) + "|" +
            (int) Math.round(moneySpentOnSales);
  }

}
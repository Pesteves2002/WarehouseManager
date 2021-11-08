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

  /** Partner Status (TO BE IMPROVED) */
  private Status status = new Normal(this, 0);

  /** Money spent on purchases by the partner */
  private int moneySpentOnPurchases;

  /** money expect to spend on purchase by the partner */
  private int moneyExpectedToSpendOnPurchases;

  /** money spent on Sales by the partner */
  private int moneySpentOnSales;

  private List<Transaction> transactionList = new LinkedList<>();

  private List<Notification> notificationList = new LinkedList<>();

  private Set<Batch> thisBatches = new TreeSet<>(new BatchComparator());

  /** Map with all the Transactions made by the Partner */
  private Map<Integer, Transaction> thisTransactions = new TreeMap<Integer, Transaction>();

  private Map<String, Product> thisNotifications = new TreeMap<>();


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

  public void addProductNotification(Product product) {
    String productKey = product.getProductKey();
    thisNotifications.put(productKey, product);
    thisNotifications.get(productKey).registerObserver(this);
  }

  public void toggleProductNotification(Product product)
  {

    if (thisNotifications.get(product.getProductKey()) == null)
    {
      addProductNotification(product);
    }
    thisNotifications.remove(product.getProductKey());
  }

  public void update(Notification notification) {
    notificationList.add(notification);
  }

  public String showAndClearNotifications() {
    String s = "\n";
    for (Notification notification: notificationList)
    {
      s += notification.toString() + "\n";
    }
    if (!s.equals("\n"))
    s.substring(0, s.length()-1);
    else {
      s = "";
    }
    notificationList.clear();
    return this.toString() + s;
  }


  public void addMoneySpentOnSales(int moneySpentOnSales) {
    this.moneySpentOnSales += moneySpentOnSales;
  }

  public void addMoneySpentOnPurchases(int moneySpentOnPurchases) {
    this.moneySpentOnPurchases += moneySpentOnPurchases;
  }

  public void addMoneyExpectedToSpendOnPurchases(int moneyExpectedToSpendOnPurchases) {
    this.moneyExpectedToSpendOnPurchases += moneyExpectedToSpendOnPurchases;
  }

  /**
   * Return the partnerID
   *
   * @return partnerID
   */
  public String getPartnerKey() {
    return partnerKey;
  }


  public Set<Batch> getThisBatches() {
    return thisBatches;
  }

  public void removeBatch(Batch batch)
  {
    thisBatches.remove(batch);
  }


  public void addTransaction(Transaction transaction) {
    transactionList.add(transaction);
    // FIX ME
    if (transaction instanceof Acquisition) {
      addMoneySpentOnPurchases((int) transaction.getBaseValue());
    }

  }

  public Map<Integer, Transaction> getThisTransactions() {
    return thisTransactions;
  }

  /**
   * Add a batch to the Tree Map
   *
   * @param batch
   */
  public void addBatch(String productKey, Batch batch) {
    thisBatches.add(batch);
  }

  public List<Transaction> getTransactionList() {
    return transactionList;
  }

  protected void setStatus(Status status) {
    this.status = status;
  }

  public double pay(int differenceOfDays, boolean productDerived, int baseValue) {
    int numberOfDays = 5;
    if (productDerived) {
      numberOfDays = 3;
    }
    if (differenceOfDays >= 0) {
      if (differenceOfDays >= numberOfDays)
        return status.p1(baseValue);
      return status.p2(baseValue, differenceOfDays);
    }
    if (-differenceOfDays <= numberOfDays)
      return status.p3(baseValue, -differenceOfDays);

    return status.p4(baseValue, -differenceOfDays);
  }


  /**
   * String representation of the Class Partner
   *
   * @return
   */
  public String toString() {

    return partnerKey + "|" + partnerName + "|" + partnerAddress + "|" + status + "|" +
            status.getPoints() + "|" + moneySpentOnPurchases + "|" +
            moneyExpectedToSpendOnPurchases + "|" +
            moneySpentOnSales;
  }

}
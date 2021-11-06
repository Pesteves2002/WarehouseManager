package ggc;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents the owner of some batches,
 * it can buy or sell some products
 */
public class Partner implements Serializable {
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


  private Set<Batch> thisBatches = new TreeSet<>(new BatchComparator());

  /** Map with all the Transactions made by the Partner */
  private Map<Integer, Transaction> thisTransactions = new TreeMap<Integer, Transaction>();

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


  public Collection<Batch> getThisBatches() {
return thisBatches;
  }


  public void addTransaction(Transaction transaction) {
    transactionList.add(transaction);

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
package ggc;

import java.io.Serializable;
import java.util.*;

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
  private Status status = new Normal();

  /** points of the partner */
  private int points;

  /** Money spent on purchases by the partner */
  private int moneySpentOnPurchases;

  /** money expect to spend on purchase by the partner */
  private int moneyExpectedToSpendOnPurchases;

  /** money spent on Sales by the partner */
  private int moneySpentOnSales;

  private List<Transaction> transactionList = new ArrayList<Transaction>();

  /** Map with all the Batches owned by the Partner */
  private Map<String, Batch> thisBatches = new TreeMap<String, Batch>(new CollatorWrapper());
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

  /**
   * Return the partnerID
   *
   * @return partnerID
   */
  public String getPartnerKey() {
    return partnerKey;
  }

  public Collection<Batch> getThisBatches() {
    return Collections.unmodifiableCollection(thisBatches.values());
  }

  public void addTransaction(Transaction transaction) {
    transactionList.add(transaction);

  }

  /**
   * Add a batch to the Tree Map
   *
   * @param batch
   */
  public void addBatch(Batch batch) {
    thisBatches.put(batch.getThisProductID(), batch);
  }

  /**
   * String representation of the Class Partner
   *
   * @return
   */
  public String toString() {
    return partnerKey + "|" + partnerName + "|" + partnerAddress + "|" + status + "|" +
            points + "|" + moneySpentOnPurchases + "|" +
            moneyExpectedToSpendOnPurchases + "|" +
            moneySpentOnSales;
  }

}
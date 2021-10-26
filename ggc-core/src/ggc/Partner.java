package ggc;

import java.io.Serializable;
import java.util.*;

public class Partner implements Serializable{

    private String partnerID;

    private String partnerName;

    private String partnerAddress;

    private Status status = new Normal();

    private int points;

    private int moneySpentOnPurchases;

    private int moneyExpectedToSpendOnPurchases;

    private int moneySpentOnSales;

    // private map of notifications

    private Map<String, Batch> thisBatches = new TreeMap<String,Batch>();

    private Map<Integer, Transaction> thisTransactions = new TreeMap<Integer,Transaction>();

    public Partner(String partnerKey, String partnerName, String partnerAddress){
        this.partnerID = partnerKey;
        this.partnerName = partnerName;
        this.partnerAddress = partnerAddress;

     }

    public String getPartnerID() {
        return partnerID;
    }

    public void addBatch (Batch batch)
     {
         // Fix - replacing previous batch if it has the same product
        thisBatches.put(batch.getThisProductID(), batch);
     }

    public String toString(){
        return partnerID + "|" + partnerName + "|" + partnerAddress + "|" + status + "|" +
                points + "|" + moneySpentOnPurchases + "|" +
                moneyExpectedToSpendOnPurchases + "|" +
                moneySpentOnSales;
     }

}
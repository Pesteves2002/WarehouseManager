package ggc;

import java.io.Serializable;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 202110262226L;

    private int transactionKey;

    private int transactionDate;

    private String partnerKey;

    private String productKey;

    private int amount;

    private double baseValue;

    private int paymentDate;

    public Transaction(int transactionKey, int transactionDate, String partnerKey, String productKey, int amount, double baseValue) {
        this.transactionKey = transactionKey;
        this.transactionDate = transactionDate;
        this.partnerKey = partnerKey;
        this.productKey = productKey;
        this.amount = amount;
        this.baseValue = baseValue;
        this.paymentDate = transactionDate;
    }

    @Override
    public String toString() {
        return transactionKey + "|" + partnerKey + "|" + productKey + "|" + amount + "|" + baseValue + "|" + paymentDate;
    }
}
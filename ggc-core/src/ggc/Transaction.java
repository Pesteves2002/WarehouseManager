package ggc;

import java.io.Serializable;

public class Transaction implements Serializable {

    private int transactionID;

    private Partner partnerID;

    private Product productID;

    private int quantity;

    private int TransactionDate;

    private int limitDate;

}
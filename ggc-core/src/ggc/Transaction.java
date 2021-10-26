package ggc;

import java.io.Serializable;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 202110262226L;

    private int transactionID;

    private Partner partnerID;

    private Product productID;

    private int quantity;

    private int TransactionDate;

    private int limitDate;

}
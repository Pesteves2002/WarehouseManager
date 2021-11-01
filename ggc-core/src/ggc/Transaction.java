package ggc;

import java.io.Serializable;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 202110262226L;

    private int transactionKey;

    private int TransactionDate;


    private Partner partnerID;

    private Product productID;

    private int quantity;


    private int limitDate;

}
package ggc;

import java.io.Serializable;

public abstract class Transaction implements Serializable {

  private static final long serialVersionUID = 202110262226L;

  private int transactionKey;

  private int transactionDate;

  private Partner partner;

  private String productKey;

  private int amount;

  private double baseValue;

  private int paymentDate;

  public Transaction(int transactionKey, int transactionDate, Partner partner, String productKey, int amount, double baseValue) {
    this.transactionKey = transactionKey;
    this.transactionDate = transactionDate;
    this.partner = partner;
    this.productKey = productKey;
    this.amount = amount;
    this.baseValue = baseValue;
    this.paymentDate = transactionDate;
  }

  public int getTransactionKey() {
    return transactionKey;
  }

  public int getTransactionDate() {
    return transactionDate;
  }

  public Partner getPartner() {
    return partner;
  }

  public String getProductKey() {
    return productKey;
  }

  public int getAmount() {
    return amount;
  }

  public double getBaseValue() {
    return baseValue;
  }

  public int getPaymentDate() {
    return paymentDate;
  }

  public  abstract String accept(TransactionVisitor tv) ;

  public abstract  double seePrice (TransactionVisitor tv, int time);

  public abstract double paySale( ShowSale tv, int warehouseDate) ;


}
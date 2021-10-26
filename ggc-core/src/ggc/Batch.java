package ggc;

import java.io.Serializable;

public class Batch implements Serializable {

    private String _product;

    private int Price;

    private int quantity;

    private double reduction = 0;

    private String _partner;


    public Batch(String thisProduct, int price, int quantity, String partner) {
        _product = thisProduct;
        Price = price;
        this.quantity = quantity;
        _partner = partner;

    }

    public Batch(String thisProduct, int price, int quantity,String partner, double reduction) {
        this(thisProduct, price, quantity, partner);
        this.reduction = reduction;

    }

    public String getThisProductID() {
        return _product;
    }

    public int getPrice() {
        return Price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String get_partner() {
        return _partner;
    }

    @Override
    public String toString() {
        return _product + "|" + _partner + "|" + Price + "|" + quantity;
    }
}
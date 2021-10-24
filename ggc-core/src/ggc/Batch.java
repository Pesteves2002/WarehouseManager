package ggc;

import java.io.Serializable;

public class Batch implements Serializable {

    private Product _product;

    private int Price;

    private int quantity;

    private double reduction = 0;

    private String _partner;


    public Batch(Product thisProduct, int price, int quantity, String partner) {
        _product = thisProduct;
        Price = price;
        this.quantity = quantity;
        _partner = partner;

    }

    public Batch(Product thisProduct, int price, int quantity,String partner, double reduction) {
        this(thisProduct, price, quantity, partner);
        this.reduction = reduction;

    }

    public String getThisProductID() {
        return _product.getProductName();
    }

    @Override
    public String toString() {
        return _product.getProductName() + "|" + _partner + "|" + Price + "|" + quantity;
    }
}
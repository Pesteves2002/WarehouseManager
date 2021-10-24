package ggc;

import java.io.Serializable;

public class Product implements Serializable {
    private String productName;

    private int maxPrice;

    private int actualStock;


    public Product(String productName) {
        this.productName = productName;
    }

    public Product(String productName, int maxPrice, int actualStock)
    {
        this(productName);
        this.maxPrice = maxPrice;
        this.actualStock = actualStock;
    }


    public String getProductName() {
        return productName;
    }

    public void addStock(int newStock)
    {
        actualStock += newStock;
    }

    public void changeMaxPrice(int value)
    {
        if (maxPrice < value)
        maxPrice = value;
    }

    @Override
    public String toString() {
        return productName + "|" +  maxPrice +  "|" + actualStock ;
    }


}
package ggc;

import java.io.Serializable;

public class Product implements Serializable{
    private String productName;



    public Product(String productName) {
        this.productName = productName;
    }

    public Product(String productName, String Recipe) {
        this.productName = productName;
    }


    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return productName ;
    }
}
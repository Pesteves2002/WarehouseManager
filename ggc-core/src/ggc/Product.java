package ggc;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Product implements Serializable {
    private String productName;

    private int maxPrice;

    private int actualStock;

    private List<Batch> _batches = new LinkedList<Batch>();


    public Product(String productName, int maxPrice, int actualStock) {

        this.productName = productName;
        this.maxPrice = maxPrice;
        this.actualStock = actualStock;
    }


    public void addStock(int newStock) {
        actualStock += newStock;
    }

    public void changeMaxPrice(int value) {
        if (maxPrice < value)
            maxPrice = value;
    }

    public void addBatch(Batch newBatch) {
        _batches.add(newBatch);
    }

    public List<Batch> get_batches() {

        return _batches;
    }

    @Override
    public String toString() {
        return productName + "|" + maxPrice + "|" + actualStock;
    }


}
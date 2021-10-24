package ggc;

import java.util.*;

public class Derived extends Product {

    private List<String> _ingredients = new ArrayList<String>();

    private Map<String, Integer> _quantityIngredients = new TreeMap<String, Integer>();

    public Derived(String productName, String Recipe) {
        super(productName);
        String[] product = Recipe.split("#");
        int i = 0;
        for (String s : product) {
            String[] components = s.split(":");
            _ingredients.add( components[0]);
            _quantityIngredients.put(components[0], Integer.parseInt(components[1]));
        }

    }

}

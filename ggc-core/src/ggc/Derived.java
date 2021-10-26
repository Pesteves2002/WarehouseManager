package ggc;

import java.util.*;

public class Derived extends Product {

    private static final long serialVersionUID = 202110262232L;

    private List<String> _ingredients = new ArrayList<String>();

    private Map<String, Integer> _quantityIngredients = new TreeMap<String, Integer>();

    private float reduction;

    public Derived(String productName, float price, int stock, String Recipe, float reduction) {
        super(productName, price, stock);
        String[] product = Recipe.split("#");
        int i = 0;
        for (String s : product) {
            String[] components = s.split(":");
            _ingredients.add( components[0]);
            _quantityIngredients.put(components[0], Integer.parseInt(components[1]));
        }
        this.reduction = reduction;

    }

    public String getReceita()
    {
        String s = "|" + reduction + "|";

        for (String i : _ingredients)
        {
            s += i + ":" + _quantityIngredients.get(i) + "#";
        }
        s = s.substring(0,s.length() -1 );
        return s;
    }

    @Override
    public String toString() {
        return super.toString() + getReceita();
    }
}

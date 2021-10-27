package ggc;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that extends Product
 * It adds the recipe of the Derived Product
 */
public class Derived extends Product {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262232L;

  /** List that contains the ingredients */
  private List<String> _ingredients = new ArrayList<String>();

  /** Map that contains the quantity of the ingridients */
  private Map<String, Integer> _quantityIngredients = new TreeMap<String, Integer>();

  /** Reduction of the Derived Product*/
  private float reduction;

  /**
   * Create a new Derived Product
   * @param productName
   * @param price
   * @param stock
   * @param Recipe
   * @param reduction
   */
  public Derived(String productName, float price, int stock, String Recipe, float reduction) {
    super(productName, price, stock);
    String[] product = Recipe.split("#");
    int i = 0;
    for (String s : product) {
      String[] components = s.split(":");
      _ingredients.add(components[0]);
      _quantityIngredients.put(components[0], Integer.parseInt(components[1]));
    }
    this.reduction = reduction;
  }

  /**
   * Get the String representation of the recipe
   * @return
   */
  public String getRecipe() {
    String s = "|" + reduction + "|";

    for (String i : _ingredients) {
      s += i + ":" + _quantityIngredients.get(i) + "#";
    }
    s = s.substring(0, s.length() - 1);
    return s;
  }

  /**
   * Get the String representation of the Derived Product
   * @return
   */
  @Override
  public String toString() {
    return super.toString() + getRecipe();
  }
}

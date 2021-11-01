package ggc;

import java.util.*;

/**
 * Class that extends Product
 * It adds the recipe of the Derived Product
 */
public class Derived extends Product {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262232L;

  /** List that contains the ingredients */
  private Set<String> ingredients = new TreeSet<>(new CollatorWrapper());

  /** Map that contains the quantity of the ingredients */
  private Map<String, Integer> quantityIngredients = new TreeMap<String, Integer>();

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
      ingredients.add(components[0]);
      quantityIngredients.put(components[0], Integer.parseInt(components[1]));
    }
    this.reduction = reduction;
  }

  /**
   * Get the String representation of the recipe
   * @return
   */
  public String getRecipe() {
    String recipe = "|" + reduction + "|";

    for (String ingredient : ingredients) {
      recipe += ingredient + ":" + quantityIngredients.get(ingredient) + "#";
    }
    recipe = recipe.substring(0, recipe.length() - 1);
    return recipe;
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

package ggc.products;

import java.util.*;

/**
 * Class that extends Product
 * It adds the recipe of the Derived Product
 */
public class Derived extends Product {
  /** Serial number for serialization. */
  private static final long serialVersionUID = 202110262232L;

  /** List that contains the ingredients */
  private List<String> ingredients = new LinkedList<>();

  /** Map that contains the quantity of the ingredients */
  private Map<String, Integer> quantityIngredients = new TreeMap<String, Integer>();

  /** Reduction of the Derived Product */
  private float reduction;

  /**
   * Create a new Derived Product
   *
   * @param productName
   * @param price
   * @param stock
   * @param recipe
   * @param reduction
   */
  public Derived(String productName, double price, int stock, String recipe, float reduction) {
    super(productName, price, stock);
    if (recipe != "") {
      String[] product = recipe.split("#");
      int i = 0;
      for (String s : product) {
        String[] components = s.split(":");

        ingredients.add(components[0]);
        quantityIngredients.put(components[0], Integer.parseInt(components[1]));
      }
    } else {
      this.ingredients = null;
      this.quantityIngredients = null;
    }

    this.reduction = reduction;
  }

  public float getReduction() {
    return reduction;
  }

  public List<String> getIngredients() {
    return ingredients;
  }

  public Integer getQuantityIngredient(String ingredient) {
    return quantityIngredients.get(ingredient);
  }

  /**
   * Get the String representation of the recipe
   *
   * @return
   */
  public String getRecipe() {
    if (ingredients == null) {
      return "";
    }
    String recipe = "|" + reduction + "|";

    for (String ingredient : ingredients) {
      recipe += ingredient + ":" + quantityIngredients.get(ingredient) + "#";
    }
    recipe = recipe.substring(0, recipe.length() - 1);
    return recipe;
  }


  public void calculateAggregationPrice(Map<String,Derived> allProducts)
  {
    double price = 0;
    if (this.getRecipe().equals("")) return;
    for (String  ingredient: ingredients)
    {
      price += allProducts.get(ingredient).getMaxPrice() * quantityIngredients.get(ingredient);
    }
    price *= (1+ this.reduction);
    setMaxPrice(price);
  }

  /**
   * Get the String representation of the Derived Product
   *
   * @return
   */
  @Override
  public String toString() {
    return super.toString() + getRecipe();
  }
}

package ggc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;

import ggc.exceptions.*;
import ggc.partners.Partner;
import ggc.products.Batch;
import ggc.products.CollatorWrapper;
import ggc.products.Derived;
import ggc.products.Product;
import ggc.transactions.*;

/**
 * Class Warehouse implements a warehouse.
 * Contains all the information about partners, products and transactions
 */
public class Warehouse implements Serializable {

  /**
   * Serial number for serialization.
   */
  private static final long serialVersionUID = 202109192006L;

  /**
   * WarehouseDate of the Warehouse
   */
  private int warehouseDate = 0;

  /**
   * Balance of the warehouse without the money from sales not paid
   */
  private double warehouseGlobalBalance = 0;

  /**
   * Balance of the warehouse accounting the current sales price
   */
  private double warehouseCurrentBalance = 0;

  /**
   * Number of transactions made
   */
  private int transactionNumber = 0;
  /**
   * List with all the transactions made in the warehouse
   */
  private List<Transaction> allTransactions = new ArrayList<>();

  /**
   * TreeMap of all the Partners,
   * given a String (partnerKey), it gives you the Partner,
   * The Comparator used sorts the map alphabetically
   */
  private Map<String, Partner> allPartners = new TreeMap<String, Partner>(new CollatorWrapper());

  /**
   * TreeMap of all the Products,
   * given a String (productName), it gives you the Product,
   * The Comparator used sorts the map alphabetically
   */
  private Map<String, Derived> allProducts = new TreeMap<String, Derived>(new CollatorWrapper());


  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException, UnknownKeyCException, DuplicateClientCException {

    try (BufferedReader in = new BufferedReader(new FileReader(txtfile))) {
      String s;
      while ((s = in.readLine()) != null) {
        String line = new String(s.getBytes(), "UTF-8");

        String[] fields = line.split("\\|");
        switch (fields[0]) {
          case "PARTNER" -> doRegisterPartner((fields[1]), (fields[2]), (fields[3]));
          case "BATCH_S" -> doRegisterBatch((fields[1]), (fields[2]), Float.parseFloat(fields[3]), Integer.parseInt(fields[4]), 0, "");
          case "BATCH_M" -> doRegisterBatch((fields[1]), (fields[2]), Float.parseFloat(fields[3]), Integer.parseInt(fields[4]), Float.parseFloat(fields[5]), fields[6]);
          default -> throw new BadEntryException(line);
        }

      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException(txtfile);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (DuplicateClientCException e) {
      throw new DuplicateClientCException(e.getDuplicateKey());
    } catch (BadEntryException e) {
      throw new BadEntryException(e.getEntrySpecification());
    } catch (UnknownKeyCException e) {
      throw new UnknownKeyCException(e.getUnknownKey());
    }
  }

  /**
   * Returns the date of this warehouse
   *
   * @return int
   */

  public int doShowDate() {
    return warehouseDate;
  }

  /**
   * Advances warehouseDate, if warehouseDate <= 0 throws Exception
   *
   * @@param timeToAdvance
   * @@throws InvalidDateException
   */
  public void doAdvanceDate(int timeToAdvance) throws InvalidDateException {
    if (timeToAdvance <= 0) throw new InvalidDateException(timeToAdvance);
    {
      warehouseDate += timeToAdvance;
    }
  }

  /**
   * Finds a Product given it productKey
   *
   * @param productKey
   * @return Derived
   * @throws UnknownProductKeyCException
   */

  public Derived doFindProduct(String productKey) throws UnknownProductKeyCException {
    for (Derived product : allProducts.values()) {
      if (productKey.compareToIgnoreCase(product.getProductKey()) == 0)
        return product;
    }
    throw new UnknownProductKeyCException(productKey);
  }

  /**
   * Returns a Collection of Products
   *
   * @return Collection<Product>
   */
  public Collection<Product> doShowAllProducts() {
    return Collections.unmodifiableCollection(allProducts.values());
  }

  /**
   * Returns a Collection of Batches
   *
   * @return Collection<Batch>
   */
  public Collection<Batch> doShowAllBatches() {
    List<Batch> allBatches = new ArrayList<Batch>();
    for (Product product : allProducts.values())
      if (product.get_batches() != null)
        for (Batch batch : product.get_batches())
          allBatches.add(batch);
    return allBatches;
  }

  /**
   * Given a partner, it will return all the batches by the partner
   *
   * @param partnerKey
   * @return Collection<Batch>
   * @throws UnknownKeyCException
   */

  public Collection<Batch> doShowBatchesByPartner(String partnerKey) throws UnknownPartnerKeyCException {
    Partner partner = doShowPartner(partnerKey);
    List<Batch> batches = new LinkedList<>();
    for (Batch batch : doShowPartner(partnerKey).getThisBatches())
      if (batch.getStock() ==0)
        partner.removeBatch(batch);
    return Collections.unmodifiableCollection(partner.getThisBatches());
  }

  /**
   * Given a product, it will return all the batches with the product
   *
   * @param productKey
   * @return Collection<Batch>
   * @throws UnknownProductKeyCException
   */
  public Collection<Batch> doShowBatchesByProduct(String productKey) throws UnknownProductKeyCException {
    Product product = doFindProduct(productKey);
    if (product.get_batches() != null)
      return Collections.unmodifiableCollection(product.get_batches());
    // if a product has no batches
    Batch dummy = new Batch("p", 0, 0, "p");
    List<Batch> dummyList = new LinkedList<Batch>();
    dummyList.add(dummy);
    return Collections.unmodifiableCollection(dummyList);
  }

  /**
   * Updates the value of unpaid sales of the partner
   *
   * @param partner
   */
  public void updatePartnerSaleValues(Partner partner) {
    double price = updateSaleTransactions(partner.getTransactionList());
    if (price >= 0)
      partner.setMoneyExpectedToSpendOnSales(price);
  }

  /**
   * Shows partner with notifications
   *
   * @param partnerKey
   * @return
   * @throws UnknownPartnerKeyCException
   */
  public String doShowPartnerNotifications(String partnerKey) throws UnknownPartnerKeyCException {
    updatePartnerSaleValues(doShowPartner(partnerKey));
    return doShowPartner(partnerKey).deliver();
  }

  /**
   * Returns a Partner given its partnerKey
   *
   * @param partnerKey
   * @return partner
   * @throws UnknownPartnerKeyCException
   */
  public Partner doShowPartner(String partnerKey) throws UnknownPartnerKeyCException {
    for (Partner partner : allPartners.values()) {
      if (partnerKey.compareToIgnoreCase(partner.getPartnerKey()) == 0) {
        updatePartnerSaleValues(partner);
        return allPartners.get(partner.getPartnerKey());
      }
    }
    throw new UnknownPartnerKeyCException(partnerKey);
  }


  /**
   * Returns a Collection with all the Partners
   *
   * @return Collection<Partner>
   */
  public Collection<Partner> doShowAllPartners() {
    for (Partner partner : allPartners.values())
      updatePartnerSaleValues(partner);
    return Collections.unmodifiableCollection(allPartners.values());
  }

  /**
   * Registers a new Partner and adds it to the Tree
   *
   * @param partnerKey
   * @param partnerName
   * @param partnerAddress
   * @throws DuplicateClientCException
   */
  void doRegisterPartner(String partnerKey, String partnerName, String partnerAddress) throws
          DuplicateClientCException {
    for (Partner partner : allPartners.values()) {
      if (partnerKey.compareToIgnoreCase(partner.getPartnerKey()) == 0)
        throw new DuplicateClientCException(partnerKey);
    }
    allPartners.put(partnerKey, new Partner(partnerKey, partnerName, partnerAddress));
  }

  /**
   * Given a partnerKey and a productKey, it will enable/disable the notifications of the partner for that product
   *
   * @param partnerKey
   * @param productKey
   * @throws UnknownPartnerKeyCException
   * @throws UnknownProductKeyCException
   */
  public void doToggleProductNotifications(String partnerKey, String productKey) throws
          UnknownPartnerKeyCException, UnknownProductKeyCException {
    Partner partner = doShowPartner(partnerKey);
    Product product = doFindProduct(productKey);
    partner.toggleProductNotification(product);
  }

  /**
   * Shows all the Acquisition transactions for the partner
   *
   * @param partnerKey
   * @return
   * @throws UnknownPartnerKeyCException
   */
  public Collection<String> doShowPartnerAcquisition(String partnerKey) throws UnknownPartnerKeyCException {
    Partner partner = doShowPartner(partnerKey);
    List<String> allAcquisitions = new LinkedList<>();

    for (Transaction transaction : partner.getTransactionList()) {
      allAcquisitions.add(transaction.accept(new ShowAcquisition(), warehouseDate));
    }
    return Collections.unmodifiableCollection(allAcquisitions);
  }

  /**
   * Shows all the sales and breakdowns paid of the partner
   *
   * @param partnerKey
   * @return
   * @throws UnknownPartnerKeyCException
   */
  public Collection<String> doShowPartnerSales(String partnerKey) throws UnknownPartnerKeyCException {
    Partner partner = doShowPartner(partnerKey);
    List<String> allAcquisitions = new LinkedList<>();

    for (Transaction transaction : partner.getTransactionList()) {
      allAcquisitions.add(transaction.accept(new ShowSaleBreakdown(),warehouseDate));
    }
    return Collections.unmodifiableCollection(allAcquisitions);
  }

  /**
   * Registers a new Batch with a Product and adds it to the Tree
   *
   * @param product
   * @param partnerKey
   * @param price
   * @param stock
   * @throws UnknownPartnerKeyCException
   */
  public void doRegisterBatch(String product, String partnerKey, double price, int stock, float reduction, String
          recipe) throws UnknownPartnerKeyCException {

    Partner partner = doShowPartner(partnerKey);

    Batch newBatch = new Batch(product, price, stock, partner.getPartnerKey());

    if (allProducts.get(product) != null) {
      allProducts.get(product).addStock(stock);
      allProducts.get(product).changeMinMaxPrice(price);
    } else {
      Derived newProduct = new Derived(product, price, stock, recipe, reduction);
      allProducts.put(product, newProduct);
      for (Partner partners : allPartners.values()) {
        partners.addProductNotification(allProducts.get(product));
      }
    }
    allProducts.get(product).addBatch(newBatch);
    partner.addBatch(newBatch.getThisProductID(), newBatch);
  }

  /**
   * Shows the transaction with the id index
   *
   * @param index
   * @return
   * @throws UnknownTransactionKeyCException
   */
  public String doShowTransaction(int index) throws UnknownTransactionKeyCException {
    if (index >= transactionNumber || index < 0) {
      throw new UnknownTransactionKeyCException(((Integer) index).toString());
    }
    return allTransactions.get(index).accept(new ShowTransaction(),warehouseDate);
  }

  /**
   * Register a breakdown transaction
   *
   * @param partnerKey
   * @param productKey
   * @param amount
   * @throws UnknownPartnerKeyCException
   * @throws UnknownProductKeyCException
   * @throws UnavailableProductCException
   */
  public void doRegisterBreakdown(String partnerKey, String productKey, int amount) throws
          UnknownPartnerKeyCException, UnknownProductKeyCException, UnavailableProductCException {
    Partner partner = doShowPartner(partnerKey);
    Derived product = doFindProduct(productKey);

    if (product.getActualStock() < amount) {
      throw new UnavailableProductCException(productKey, amount, product.getActualStock());
    }
    // if product is not derived
    if (product.getRecipe().equals("")) return;

    double initialValue = 0;
    double finalValue = 0;

    // purchase part
    initialValue = auxSale(product, amount);

    String components = "";

    for (String ingredient : product.getIngredients()) {
      double price;
      Derived component = doFindProduct(ingredient);
      if (component.getActualStock() == 0) {
        // gets the biggest price
        price = component.getMaxPrice();
      } else {
        // gets the lowest price
        price = component.get_batches().getFirst().getPrice();
      }
      doRegisterBatch(ingredient, partner.getPartnerKey(), price, amount* product.getQuantityIngredient(ingredient), component.getReduction(), component.getRecipe());
      finalValue += price * product.getQuantityIngredient(ingredient) * amount;
      components += ingredient + ":" + product.getQuantityIngredient(ingredient) * amount + ":" + (int) Math.round (price * amount* product.getQuantityIngredient(ingredient)) + "#";

    }
    components = components.substring(0, components.length() - 1);

    double payment = initialValue - finalValue;
    if (payment > 0) {
      changeGlobalBalance(payment);
      partner.addPoints( payment * 10);
    }
    Breakdown breakdown = new Breakdown(transactionNumber++, warehouseDate, partner, productKey, amount, payment, components);
    allTransactions.add(breakdown);
    partner.addTransaction(breakdown);

  }

  /**
   * Given a product and an amount, it will take out the amount from batches of the product
   *
   * @param product
   * @param amount
   * @return price
   */

  public double auxSale(Derived product, int amount) throws UnknownProductKeyCException {
    double price = 0;

    for (Batch batch : product.get_batches()) {
      // enough product in a batch
      if (amount < batch.getStock()) {
        batch.decreaseStock(amount);
        product.reduceStock(amount);
        price += batch.getPrice() * amount;
        amount = 0;
        break;
      }
      if (amount == batch.getStock()) {
        price += batch.emptyStock();
        product.clearAllStock();
        product.calculateAggregationPrice(allProducts);
        amount = 0;
        break;
      }
      // not enough product in a batch
      else {
        int numberProducts = batch.getStock();
        price += batch.emptyStock();
        product.get_batches().remove(batch);
        amount -= numberProducts;
        product.reduceStock(numberProducts);

      }
    }
    // if it clears all the batches and there's still product to make
    if (amount != 0) {
      double aggregation = 0;
      for (String components : product.getIngredients())
        aggregation += auxSale(doFindProduct(components), amount * product.getQuantityIngredient(components));
      aggregation *= (1 + product.getReduction());
      price += aggregation;

    }
    return price;
  }

  /**
   * Creates a new sale transaction
   *
   * @param partnerKey
   * @param productKey
   * @param amount
   * @param deadline
   * @throws UnknownProductKeyCException
   * @throws UnavailableProductCException
   * @throws UnknownPartnerKeyCException
   */

  public void doRegisterSaleTransaction(String partnerKey, String productKey, int amount, int deadline) throws
          UnknownProductKeyCException, UnavailableProductCException, UnknownPartnerKeyCException {
    try {
      Partner partner = doShowPartner(partnerKey);
      Derived product = doFindProduct(productKey);
      double price = 0;
      Sale sale;
      // if product is simple
      if (product.getRecipe().equals("")) {
        if (product.getActualStock() < amount)
          throw new UnavailableProductCException(productKey, amount, product.getActualStock());

        price = auxSale(product, amount);
        sale = new Sale(transactionNumber++, warehouseDate, partner, product.getProductKey(), amount, price, deadline, false, false);
      } else { // derived products
        if (product.getActualStock() >= amount) {
          // if there's enough derived products
          price = auxSale(product, amount);
        } else { // if it's needed to create amount

          int amountNeeded = amount - product.getActualStock();

          // check if there's enough components
          for (String ingredient : product.getIngredients()) {
            seeExistenceRecursive(allProducts, ingredient, product.getQuantityIngredient(ingredient) * amountNeeded);
          }

          price = product.clearAllStock();

          while (amountNeeded > 0) {
            double aggregationPrice = 0;
            for (String ingredient : product.getIngredients()) {
              int amountIngredient = doFindProduct(productKey).getQuantityIngredient(ingredient);
              aggregationPrice += auxSale(doFindProduct(ingredient), amountIngredient);
            }
            aggregationPrice *= (1 + product.getReduction());
            price += aggregationPrice;
            amountNeeded--;
          }
          product.calculateAggregationPrice(allProducts);
        }
        sale = new Sale(transactionNumber++, warehouseDate, partner, product.getProductKey(), amount, price, deadline, false, true);
      }

      partner.addTransaction(sale);
      allTransactions.add(sale);

    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownPartnerKeyCException(e.getUnknownKey());
    } catch (UnknownProductKeyCException e) {
      throw new UnknownProductKeyCException(e.getUnknownKey());
    }
  }

  /**
   * Check if a product can be created recursively
   *
   * @param products
   * @param ingredient
   * @param amount
   * @throws UnavailableProductCException
   */

  public void seeExistenceRecursive(Map<String, Derived> products, String ingredient, int amount) throws UnavailableProductCException {
    try {
      if (amount <= products.get(doFindProduct(ingredient).getProductKey()).getActualStock()) return;

      // if product is simple
      if (products.get(doFindProduct(ingredient).getProductKey()).getRecipe().equals(""))
        throw new UnavailableProductCException(ingredient, amount, products.get(doFindProduct(ingredient).getProductKey()).getActualStock());

      // starts recursion for the components of the product
      for (String components : doFindProduct(ingredient).getIngredients()) {
        seeExistenceRecursive(products, components, (amount - products.get(doFindProduct(ingredient).getProductKey()).getActualStock()) * doFindProduct(ingredient).getQuantityIngredient(components));
      }
    }//shouldn't happen
    catch (UnknownProductKeyCException e) {
      e.printStackTrace();
    }
  }

  /**
   * Register a new Transaction, if a product is not known it returns false
   *
   * @param partnerKey
   * @param productKey
   * @param price
   * @param amount
   * @return boolean
   * @throws UnknownPartnerKeyCException
   */

  public boolean doRegisterAcquisitionTransaction(String partnerKey, String productKey, double price, int amount) throws
          UnknownPartnerKeyCException {

    try {
      Partner partner = doShowPartner(partnerKey);
      Derived product = doFindProduct(productKey);
      if (product != null) {
        doRegisterBatch(product.getProductKey(), partner.getPartnerKey(), price, amount, product.getReduction(), product.getRecipe());
        Acquisition acquisition = new Acquisition(transactionNumber++, warehouseDate, partner, product.getProductKey(), amount, amount * price);
        allTransactions.add(acquisition);
        partner.addTransaction(acquisition);
        price = -price * amount;
        partner.addMoneySpentOnPurchases(-price);
        changeGlobalBalance(price);
        return true;
      } else {
        return false;
      }
    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownPartnerKeyCException(e.getUnknownKey());
    } catch (UnknownProductKeyCException e) {
      return false;
    }
  }

  public void doRegisterTransactionNewProduct(String productKey, String partnerKey, double price, int amount,
                                              float reduction, String recipe) throws UnknownPartnerKeyCException {
    try {
      Partner partner = doShowPartner(partnerKey);
      price *= amount;
      Acquisition acquisition = new Acquisition(transactionNumber++, warehouseDate, partner, productKey, amount, price);
      allTransactions.add(acquisition);
      partner.addTransaction(acquisition);
      partner.addMoneySpentOnPurchases(price);
      changeGlobalBalance(-price);

    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownPartnerKeyCException(e.getUnknownKey());
    }

  }

  /**
   * Pays the sale transaction
   *
   * @param transactionKey
   * @throws UnknownTransactionKeyCException
   */
  public void doReceivePayment(int transactionKey) throws UnknownTransactionKeyCException {
    if (transactionKey >= transactionNumber || transactionKey < 0) {
      throw new UnknownTransactionKeyCException(((Integer) transactionKey).toString());
    }

    Transaction transaction = allTransactions.get(transactionKey);

    double value = transaction.paySale(new ShowSale(), warehouseDate);
    Partner partner = transaction.getPartner();
    partner.addMoneySpentOnSales(value);
    warehouseGlobalBalance += (value);
  }

  /**
   * Creates a Collection with all batches under a Given Prices
   *
   * @param priceLimit
   * @return Collection<Batch>
   */
  public Collection<Batch> doLookupProductBatchesUnderGivenPrice(int priceLimit) {
    List<Batch> batchesUnderGivenPrice = new ArrayList<>();
    for (Product product : allProducts.values()) {
      if (product.get_batches() != null)
        for (Batch batch : product.get_batches()) {
          if (batch.getPrice() < priceLimit)
            batchesUnderGivenPrice.add(batch);
        }
    }
    return Collections.unmodifiableCollection(batchesUnderGivenPrice);
  }

  /**
   * Return the transactions paid by the partner
   *
   * @param partnerKey
   * @return Collection<String>
   * @throws UnknownPartnerKeyCException
   */
  public Collection<String> doLookupPaymentsByPartner(String partnerKey) throws UnknownPartnerKeyCException {
    Partner partner = doShowPartner(partnerKey);
    List<String> transactions = new LinkedList<>();
    for (Transaction transaction : partner.getTransactionList()) {
      transactions.add(transaction.accept(new ShowSale(),warehouseDate));
    }
    return Collections.unmodifiableCollection(transactions);
  }

  /**
   * Calculates the current balance iterating through the transaction list
   *
   * @param transactionsList
   * @return
   */

  public double updateSaleTransactions(List<Transaction> transactionsList) {
    double currentBalance = 0;
    for (Transaction transaction : transactionsList) {
      currentBalance += transaction.seePrice(new ShowTransaction(), warehouseDate);
    }
    return Math.round(currentBalance);
  }

  /**
   * Returns the Global balance of the warehouse
   *
   * @return double
   */

  public double doShowGlobalBalance() {
    return warehouseGlobalBalance;
  }

  /**
   * Adds or subtracts an amount to the global balance of the warehouse
   *
   * @return double
   */

  public void changeGlobalBalance(double amount) {
    this.warehouseGlobalBalance += amount;
  }

  /**
   * Calls the method updateSaleTransaction
   *
   * @return double
   */

  public double doShowCurrentBalance() {
    return updateSaleTransactions(allTransactions);
  }

}


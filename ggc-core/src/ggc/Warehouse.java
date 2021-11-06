package ggc;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import ggc.exceptions.*;

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
   * Time of the Warehouse
   */

  private int time = 0;

  private double warehouseGlobalBalance = 0;

  private int transactionNumber = 0;

  List<Transaction> allTransactions = new ArrayList<>();

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

  int doShowTime() {
    return time;
  }

  /**
   * Advances time, if time <= 0 throws Exception
   *
   * @@param timeToAdvance
   * @@throws InvalidDateException
   */
  void doAdvanceTime(int timeToAdvance) throws InvalidDateException {
    if (timeToAdvance <= 0) throw new InvalidDateException(timeToAdvance);
    {
      time += timeToAdvance;
    }
  }

  /**
   * Registers a new Partner and adds it to the Tree
   *
   * @param partnerKey
   * @param partnerName
   * @param partnerAddress
   * @throws DuplicateClientCException
   */

  void doRegisterPartner(String partnerKey, String partnerName, String partnerAddress) throws DuplicateClientCException {
    for (Partner partner : allPartners.values()) {
      if (partnerKey.compareToIgnoreCase(partner.getPartnerKey()) == 0)
        throw new DuplicateClientCException(partnerKey);
    }
    allPartners.put(partnerKey, new Partner(partnerKey, partnerName, partnerAddress));
  }

  /**
   * Returns a Partner given its partnerKey
   *
   * @param partnerKey
   * @return
   * @throws UnknownKeyCException
   */
  public Partner doShowPartner(String partnerKey) throws UnknownPartnerKeyCException {

    for (Partner partner : allPartners.values()) {
      if (partnerKey.compareToIgnoreCase(partner.getPartnerKey()) == 0)
        return allPartners.get(partner.getPartnerKey());
    }
    throw new UnknownPartnerKeyCException(partnerKey);
  }

  public Derived doFindProduct(String productKey) throws UnknownProductKeyCException {

    for (Derived product : allProducts.values()) {
      if (productKey.compareToIgnoreCase(product.getProductKey()) == 0)
        return product;
    }
    throw new UnknownProductKeyCException(productKey);
  }

  /**
   * Returns a Collection with all the Partners
   *
   * @return Collection<Partner>
   */
  public Collection<Partner> doShowAllPartners() {
    return Collections.unmodifiableCollection(allPartners.values());
  }


  /**
   * Registers a new Batch with a Product and adds it to the Tree
   *
   * @param product
   * @param partnerKey
   * @param price
   * @param stock
   * @throws UnknownKeyCException
   */


  public void doRegisterBatch(String product, String partnerKey, double price, int stock, float reduction, String recipe) throws UnknownPartnerKeyCException {

    Partner partner = doShowPartner(partnerKey);

    Batch newBatch = new Batch(product, price, stock, partner.getPartnerKey(), reduction);

    if (allProducts.get(product) != null) {
      allProducts.get(product).addStock(stock);
      allProducts.get(product).changeMaxPrice(price);
    } else {
      Derived newProduct = new Derived(product, price, stock, recipe, reduction);
      allProducts.put(product, newProduct);
    }
    allProducts.get(product).addBatch(newBatch);

    partner.addBatch(newBatch.getThisProductID(), newBatch);
  }


  /**
   * Returns a Collection of Products
   *
   * @return Collection<Product>
   */
  public Collection<Product> doShowAllProducts() {
    return Collections.unmodifiableCollection(allProducts.values());
  }

  public String doShowTransaction(int index) throws UnknownTransactionKeyCException {
    if (index >= transactionNumber) {
      throw new UnknownTransactionKeyCException(((Integer) index).toString());
    }

    return allTransactions.get(index).accept(new ShowTransaction());
  }

  /**
   * Returns a Collection of Batches
   *
   * @return Collection<Batch>
   */
  public Collection<Batch> doShowAllBatches() {
    List<Batch> allBatches = new ArrayList<Batch>();
    for (Product product : allProducts.values())
      for (Batch batch : product.get_batches())
        allBatches.add(batch);

    return allBatches;
  }

  public Collection<Batch> doShowBatchesByPartner(String partnerKey) throws UnknownKeyCException {
    Partner partner = doShowPartner(partnerKey);
    List<Batch> batches = new LinkedList<>();
    for (Batch batch : partner.getThisBatches()) {
      batches.add(batch);
    }
    return Collections.unmodifiableCollection(batches);

  }

  public Collection<Batch> doShowBatchesByProduct(String productKey) throws UnknownKeyCException {

    for (Product product : allProducts.values()) {
      if (productKey.compareToIgnoreCase(product.getProductKey()) == 0) {
        return Collections.unmodifiableCollection(product.get_batches());
      }
    }
    throw new UnknownProductKeyCException(productKey);
  }

  public Collection<Batch> doLookupProductBatchesUnderGivenPrice(int priceLimit) {
    List<Batch> batchesUnderGivenPrice = new ArrayList<>();
    for (Product product : allProducts.values()) {
      for (Batch batch : product.get_batches()) {
        if (batch.getPrice() < priceLimit)
          batchesUnderGivenPrice.add(batch);
      }
    }
    return Collections.unmodifiableCollection(batchesUnderGivenPrice);
  }


  public boolean doRegisterAcquisitionTransaction(String partnerKey, String productKey, double price, int amount) throws UnknownPartnerKeyCException {

    try {
      Partner partner = doShowPartner(partnerKey);
      Derived product = doFindProduct(productKey);
      if (product != null) {
        doRegisterBatch(product.getProductKey(), partner.getPartnerKey(), price, amount, product.getReduction(), product.getRecipe());
        Acquisition acquisition = new Acquisition(transactionNumber++, time, partner.getPartnerKey(), product.getProductKey(), amount, amount * price);
        allTransactions.add(acquisition);
        partner.addTransaction(acquisition);
        price = -price * amount;
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

  public void doRegisterTransaction(String productKey, String partnerKey, double price, int amount, float reduction, String recipe) throws UnknownPartnerKeyCException {
    try {
      Partner partner = doShowPartner(partnerKey);
      Acquisition acquisition = new Acquisition(transactionNumber++, time, partner.getPartnerKey(), productKey, amount, price);
      allTransactions.add(acquisition);
      partner.addTransaction(acquisition);

    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownPartnerKeyCException(e.getUnknownKey());
    }

  }

  public void doRegisterSaleTransaction(String partnerKey, String productKey, int amount, int deadline) throws UnknownPartnerKeyCException, UnknownProductKeyCException, UnavailableProductCException {
    try {
      Partner partner = doShowPartner(partnerKey);
      Derived product = doFindProduct(productKey);

      if (product.getRecipe().equals("")) {
        if (product.getActualStock() < amount)
          throw new UnavailableProductCException(productKey, amount, product.getActualStock());
        // Metodo para retirar produtos simples
        int price = 0;
        int initialAmount = amount;
        for (Batch batch : product.get_batches()) {
          // produto suficiente numa batch
          if (amount <= batch.getStock()) {
            batch.decreaseStock(amount);
            product.reduceStock(amount);
            price += batch.getPrice() * amount;
            break;
          }
          // produto nao suficiente numa batch
          else {
            int numberProducts = batch.getStock();
            price += batch.emptyStock();
            amount -= numberProducts;
            product.reduceStock(numberProducts);

          }
        }
        Sale sale = new Sale(transactionNumber++, time, partner.getPartnerKey(), product.getProductKey(), initialAmount, price, deadline, false);
        partner.addTransaction(sale);
        allTransactions.add(sale);

      } else {

        int initialAmount = amount;
        if (product.getActualStock() > amount) { // Se existir derivado suficiente
          int price = 0;
          for (Batch batch : product.get_batches()) {
            // produto suficiente numa batch
            if (amount <= batch.getStock()) {
              batch.decreaseStock(amount);
              product.reduceStock(amount);
              price += batch.getPrice() * amount;
              break;
            } // produto nao suficiente numa batch
            else {
              int numberProducts = batch.getStock();
              price += batch.emptyStock();
              amount -= numberProducts;
              product.reduceStock(numberProducts);
              price += batch.getPrice() * numberProducts;
            }
          }
          Sale sale = new Sale(transactionNumber++, time, partner.getPartnerKey(), product.getProductKey(), initialAmount, price, deadline, false);
          partner.addTransaction(sale);
          allTransactions.add(sale);

        } else { // se for preciso criar derivado

          int amountNeeded = amount - product.getActualStock();
          // check if there's enough components
          for (String ingredient : product.getIngredients()) {
            if (product.getQuantityIngredient(ingredient) * amountNeeded > doFindProduct(ingredient).getActualStock()) {
              // Excecao de qual (?)
              throw new UnavailableProductCException(ingredient, amount, product.getActualStock());
            }
          }

          int price = product.clearAllStock();

          while (amountNeeded > 0) {
            int aggregationPrice = 0;
            for (String ingredient : product.getIngredients()) {
              int amountIngredient = doFindProduct(productKey).getQuantityIngredient(ingredient);

              while (amountIngredient > 0) {
                for (Batch batch : doFindProduct(ingredient).get_batches()) {
                  // produto suficiente numa batch
                  if (amountIngredient <= batch.getStock()) {
                    batch.decreaseStock(amountIngredient);
                    doFindProduct(ingredient).addStock(-amountIngredient);
                    aggregationPrice += batch.getPrice() * amountIngredient;
                    amountIngredient = 0;
                    break;
                  } // produto nao suficiente numa batch
                  else {
                    int numberProducts = batch.emptyStock();
                    batch.decreaseStock(numberProducts);
                    doFindProduct(ingredient).addStock(-numberProducts);
                    amountIngredient -= numberProducts;
                    aggregationPrice += batch.getPrice() * numberProducts;
                  }
                }
              }
            }
            aggregationPrice *= (1 + product.getReduction());
            price += aggregationPrice;
            amountNeeded--;

          }

          Sale sale = new Sale(transactionNumber++, time, partner.getPartnerKey(), product.getProductKey(), initialAmount, price, deadline, false);
          partner.addTransaction(sale);
          allTransactions.add(sale);
        }

      }


    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownProductKeyCException(e.getUnknownKey());
    } catch (UnknownProductKeyCException e) {
      throw new UnknownProductKeyCException(e.getUnknownKey());
    }

  }


  public Collection<String> doShowPartnerAcquisition(String partnerkey) throws UnknownPartnerKeyCException {
    Partner partner = doShowPartner(partnerkey);
    List<String> allAcquisitions = new LinkedList<>();

    for (Transaction transaction : partner.getTransactionList()) {
      allAcquisitions.add(transaction.accept(new ShowAcquisition()));
    }
    return Collections.unmodifiableCollection(allAcquisitions);
  }

  public Collection<String> doShowPartnerSales(String partnerKey) throws UnknownPartnerKeyCException {
    Partner partner = doShowPartner(partnerKey);
    List<String> allAcquisitions = new LinkedList<>();

    for (Transaction transaction : partner.getTransactionList()) {
      allAcquisitions.add(transaction.accept(new ShowSaleBreakdown()));
    }
    return Collections.unmodifiableCollection(allAcquisitions);
  }


  public double doShowGlobalBalance() {
    return warehouseGlobalBalance;
  }

  public void changeGlobalBalance(double amount) {
    this.warehouseGlobalBalance += amount;
  }

  public void doReceivePayment(int transactionKey) throws UnknownTransactionKeyCException {
    // Fix for purchase and desaggregations
    if (transactionKey >= transactionNumber || transactionKey < 0) {
      throw new UnknownTransactionKeyCException(((Integer) transactionKey).toString());
    }
    Sale sale = (Sale) allTransactions.get(transactionKey);
    sale.setPaymentDate(time);
  }

  public Collection<Transaction> doLookupPaymentsByPartner(String partnerKey) throws UnknownPartnerKeyCException {
    Partner partner = doShowPartner(partnerKey);
    return Collections.unmodifiableCollection(partner.getThisTransactions().values());

  }

  public void doRegisterBreakdown(String partnerKey, String productKey, int amount) throws UnknownPartnerKeyCException, UnknownProductKeyCException, UnavailableProductCException {
    Partner partner = doShowPartner(partnerKey);
    Derived product = doFindProduct(productKey);

    if (product.getActualStock() < amount) {
      throw new UnavailableProductCException(productKey, amount, product.getActualStock());
    }
    if (product.getRecipe().equals("")) return;

    int initialValue = 0;
    int finalValue = 0;
    int auxAmount = amount;


    for (Batch batch : product.get_batches()) {
      if (auxAmount > batch.getStock()) {
        auxAmount -= batch.getStock();
        initialValue += batch.emptyStock();
      } else {
        initialValue += batch.getPrice() * auxAmount;
        batch.decreaseStock(auxAmount);
        auxAmount = 0;
        break;
      }
    }
    String components = "";


    for (String ingredient : product.getIngredients()) {
      if (doFindProduct(ingredient).getActualStock() == 0) {
        doRegisterBatch(ingredient, partner.getPartnerKey(), doFindProduct(ingredient).getMaxPrice(), product.getQuantityIngredient(ingredient), doFindProduct(ingredient).getReduction(), doFindProduct(ingredient).getRecipe());
        finalValue += doFindProduct(ingredient).getMaxPrice() * product.getQuantityIngredient(ingredient)* amount;
        components += ingredient + ":" + product.getQuantityIngredient(ingredient)* amount + ":" + doFindProduct(ingredient).getMaxPrice() * product.getQuantityIngredient(ingredient)* amount + "#";
      } else {
        int price = (int) doFindProduct(ingredient).get_batches().first().getPrice();
        doRegisterBatch(ingredient, partner.getPartnerKey(), price, product.getQuantityIngredient(ingredient), doFindProduct(ingredient).getReduction(), doFindProduct(ingredient).getRecipe());
        finalValue += price * product.getQuantityIngredient(ingredient)* amount;
        components += ingredient + ":" + product.getQuantityIngredient(ingredient)* amount + ":" + price + "#";
      }

    }
    components.substring(0, components.length() -1);
    int payment = initialValue - finalValue;
    if (payment > 0)
    {
      changeGlobalBalance(payment);
    }
     Breakdown breakdown = new Breakdown(transactionNumber++,time,partnerKey,productKey,amount,payment,components);
    allTransactions.add(breakdown);
    partner.addTransaction(breakdown);

  }

}


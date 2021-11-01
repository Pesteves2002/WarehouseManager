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

  private int transactionKey = 0;

  List<Transaction> allTransactions= new ArrayList<>();

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
  public Partner doShowPartner(String partnerKey) throws UnknownKeyCException {

    for (Partner partner : allPartners.values()) {
      if (partnerKey.compareToIgnoreCase(partner.getPartnerKey()) == 0)
        return allPartners.get(partner.getPartnerKey());
    }
    throw new UnknownKeyCException(partnerKey);
  }

  public Derived doFindProduct(String productKey) {

    for (Derived product : allProducts.values()) {
      if (productKey.compareToIgnoreCase(product.getProductKey()) == 0)
        return product;
    }
    return null;
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


  public void doRegisterBatch(String product, String partnerKey, double price, int stock, double reduction, String recipe) throws UnknownKeyCException {

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

    partner.addBatch(newBatch);
  }


  /**
   * Returns a Collection of Products
   *
   * @return Collection<Product>
   */
  public Collection<Product> doShowAllProducts() {
    return Collections.unmodifiableCollection(allProducts.values());
  }

  public Transaction doShowTransaction(int index) throws UnknownTransactionKeyCException{
    if (index > transactionKey) {throw new UnknownTransactionKeyCException(((Integer) index).toString());}
    return allTransactions.get(index);
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
    return partner.getThisBatches();

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


  public boolean doRegisterAcquisitionTransaction(String partnerKey, String productKey, double price, int amount) throws UnknownKeyCException {

    try {
      Partner partner = doShowPartner(partnerKey);
      Derived product = doFindProduct(productKey);
      if (product != null) {
        doRegisterBatch(product.getProductKey(), partner.getPartnerKey(), price, amount, product.getReduction(), product.getRecipe());
        price = -price;
        changeGlobalBalance(price);
        Acquisition acquisition = new Acquisition(transactionKey++, time, partner.getPartnerKey(),product.getProductKey(),amount,price);
        allTransactions.add(acquisition);
        return true;
      } else {
        return false;
      }
    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownPartnerKeyCException(e.getUnknownKey());
    }
  }

  public double doShowGlobalBalance() {
    return warehouseGlobalBalance;
  }

  public void changeGlobalBalance(double amount) {
    this.warehouseGlobalBalance += amount;
  }

}


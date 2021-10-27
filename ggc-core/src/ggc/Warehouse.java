package ggc;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

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

  // Insert by ID - Maybe use normal list
  // private Map<Integer, Transaction> allTransactions = new TreeMap<Integer, Transaction>();

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
  private Map<String, Product> allProducts = new TreeMap<String, Product>(new CollatorWrapper());


  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */, UnknownPartnerKeyCException, DuplicateClientCException {
    //FIXME implement method
    // super(txtfile);

    try (BufferedReader in = new BufferedReader(new FileReader(txtfile))) {
      String s;
      while ((s = in.readLine()) != null) {
        String line = new String(s.getBytes(), "UTF-8");

        String[] fields = line.split("\\|");
        switch (fields[0]) {
          case "PARTNER" -> doRegisterPartner((fields[1]), (fields[2]), (fields[3]));
          case "BATCH_S" -> doRegisterBatchS((fields[1]), (fields[2]), Float.parseFloat(fields[3]), Integer.parseInt(fields[4]));
          case "BATCH_M" -> doRegisterBatchM((fields[1]), (fields[2]), Float.parseFloat(fields[3]), Integer.parseInt(fields[4]), Float.parseFloat(fields[5]), fields[6]);
          default -> throw new BadEntryException(line);
        }

      }
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException(txtfile);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (DuplicateClientCException e) {
      throw new DuplicateClientCException(e.get_duplicateID());
    } catch (BadEntryException e) {
      throw new BadEntryException(e.getEntrySpecification());
    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownPartnerKeyCException(e.getUnknownPartnerKey());
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
   * Registes a new Partner and adds it to the Tree
   *
   * @param partnerKey
   * @param partnerName
   * @param partnerAddress
   * @throws DuplicateClientCException
   */

  void doRegisterPartner(String partnerKey, String partnerName, String partnerAddress) throws DuplicateClientCException {
    for (Partner p : allPartners.values()) {
      if (partnerKey.compareToIgnoreCase(p.getPartnerID()) == 0)
        throw new DuplicateClientCException(partnerKey);
    }
    allPartners.put(partnerKey, new Partner(partnerKey, partnerName, partnerAddress));
  }

  /**
   * Returns a Partner given its partnerKey
   *
   * @param partnerKey
   * @return
   * @throws UnknownPartnerKeyCException
   */
  public Partner doShowPartner(String partnerKey) throws UnknownPartnerKeyCException {

    for (Partner p : allPartners.values()) {
      if (partnerKey.compareToIgnoreCase(p.getPartnerID()) == 0)
        return allPartners.get(p.getPartnerID());
    }
    throw new UnknownPartnerKeyCException(partnerKey);
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
   * Registers a new Batch with a simple Product and adds it to the Tree
   *
   * @param product
   * @param partnerKey
   * @param price
   * @param stock
   * @throws UnknownPartnerKeyCException
   */

  public void doRegisterBatchS(String product, String partnerKey, float price, int stock) throws UnknownPartnerKeyCException {

    if (allPartners.get(partnerKey) == null) {
      throw new UnknownPartnerKeyCException(partnerKey);
    }
    Batch newBatch = new Batch(product, price, stock, partnerKey);

    if (allProducts.get(product) != null) {
      allProducts.get(product).addStock(stock);
      allProducts.get(product).changeMaxPrice(price);
    } else {
      Product newProduct = new Product(product, price, stock);
      allProducts.put(product, newProduct);
    }
    allProducts.get(product).addBatch(newBatch);

    Partner p1 = allPartners.get(partnerKey);
    p1.addBatch(newBatch);

  }

  /**
   * Registers a new Batch with a Derived Product and adds it to the Tree
   *
   * @param product
   * @param partnerKey
   * @param price
   * @param stock
   * @throws UnknownPartnerKeyCException
   */


  public void doRegisterBatchM(String product, String partnerKey, float price, int stock, float reduction, String recipe) throws UnknownPartnerKeyCException {

    if (allPartners.get(partnerKey) == null) {
      throw new UnknownPartnerKeyCException(partnerKey);
    }
    Batch newBatch = new Batch(product, price, stock, partnerKey, reduction);

    if (allProducts.get(product) != null) {
      allProducts.get(product).addStock(stock);
      allProducts.get(product).changeMaxPrice(price);
    } else {
      Derived newProduct = new Derived(product, price, stock, recipe, reduction);
      allProducts.put(product, newProduct);
    }
    allProducts.get(product).addBatch(newBatch);

    Partner p1 = allPartners.get(partnerKey);
    p1.addBatch(newBatch);
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
    List<Batch> _allBatches = new LinkedList<Batch>();
    for (Product product : allProducts.values())
      for (Batch batch : product.get_batches())
        _allBatches.add(batch);

    return _allBatches;
  }

}


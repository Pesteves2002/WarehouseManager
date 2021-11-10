package ggc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

import ggc.exceptions.*;

/**
 * Façade for access.
 */
public class WarehouseManager {

  /**
   * Name of file storing current store.
   */
  private String _filename = "";

  /**
   * The warehouse itself.
   */
  private Warehouse _warehouse = new Warehouse();
  /**
   * Bit that indicates if changes were made
   */
  private boolean dirtyBit = true;


  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    if (_filename == "") {
      throw new MissingFileAssociationException();
    }
    if (dirtyBit) {
      try {
        ObjectOutputStream out = new ObjectOutputStream((new FileOutputStream(_filename)));
        out.writeObject(_warehouse);
        out.close();
        dirtyBit = false;

      } catch (FileNotFoundException e) {
        throw new FileNotFoundException();
      }
    }
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException, IOException, ClassNotFoundException {
    try (ObjectInputStream in =
                 new ObjectInputStream((new FileInputStream(filename)))) {
      _warehouse = (Warehouse) in.readObject();
      _filename = filename;
      dirtyBit = true;
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException | UnknownKeyCException | DuplicateClientCException e) {

      try {
        load(textfile);
      } catch (ClassNotFoundException | UnavailableFileException | IOException f) {
        throw new ImportFileException(textfile);
      }
    }
    dirtyBit = true;
  }

  /**
   * Returns the time of the Warehouse
   *
   * @return time
   */
  public int time() {
    return _warehouse.doShowDate();
  }

  /**
   * Gives the Warehouse the time to advance
   *
   * @@param timeToAdvance
   * @@throws InvalidDateException
   */
  public void AdvanceTime(int timeToAdvance) throws InvalidDateException {
    _warehouse.doAdvanceDate(timeToAdvance);
    dirtyBit = true;
  }

  /**
   * Returns a Collection with all the Products
   *
   * @return Collection<Product>
   */
  public Collection<Product> showAllProducts() {
    return _warehouse.doShowAllProducts();
  }

  /**
   * Returns a Collection with all the Batches
   *
   * @return Collection<Batch>
   */
  public Collection<Batch> showAllBatches() {
    return _warehouse.doShowAllBatches();
  }


  /**
   * Returns a Collection with all the Batches owned by a Partner
   *
   * @return Collection<Batch>
   */
  public Collection<Batch> showBatchesByPartner(String partnerKey) throws UnknownKeyCException {
    return _warehouse.doShowBatchesByPartner(partnerKey);
  }

  /**
   * Returns a Collection with all the Batches of a Product
   *
   * @param productKey
   * @return
   * @throws UnknownKeyCException
   */
  public Collection<Batch> showBatchesByProduct(String productKey) throws UnknownKeyCException {
    return _warehouse.doShowBatchesByProduct(productKey);
  }

  /**
   * Given an id, it returns the partner with that id
   *
   * @param id
   * @return Partner
   * @throws UnknownKeyCException
   */
  public String showPartner(String id) throws UnknownKeyCException {
    return _warehouse.doShowPartnerNotifications(id);
  }

  /**
   * Returns a Collection with all the Partners
   *
   * @return Collection<Partner>
   */
  public Collection<Partner> ShowAllPartners() {
    return _warehouse.doShowAllPartners();
  }


  /**
   * Gives the Warehouse everything to register a new Partner
   *
   * @param key
   * @param name
   * @param address
   * @throws DuplicateClientCException
   */

  public void registerPartner(String key, String name, String address) throws DuplicateClientCException {
    _warehouse.doRegisterPartner(key, name, address);
    dirtyBit = true;
  }

  /**
   * Toggles the notifications of a product to the partner
   *
   * @param partnerKey
   * @param productKey
   * @throws UnknownPartnerKeyCException
   * @throws UnknownProductKeyCException
   */

  public void toggleProductNotifications(String partnerKey, String productKey) throws UnknownPartnerKeyCException, UnknownProductKeyCException {
    _warehouse.doToggleProductNotifications(partnerKey, productKey);
    dirtyBit = true;
  }

  /**
   * Shows the acquisitions made by the partner
   *
   * @param partnerKey
   * @return Collection<String>
   * @throws UnknownPartnerKeyCException
   */

  public Collection<String> showPartnerAcquisitions(String partnerKey) throws UnknownPartnerKeyCException {
    return _warehouse.doShowPartnerAcquisition(partnerKey);
  }

  /**
   * Shows the sales and breakdowns made by the partner
   *
   * @param partnerKey
   * @return Collection<String>
   * @throws UnknownPartnerKeyCException
   */

  public Collection<String> showPartnerSales(String partnerKey) throws UnknownPartnerKeyCException {
    return _warehouse.doShowPartnerSales(partnerKey);
  }

  /**
   * Shows a transaction
   *
   * @param index
   * @return representation of a transaction
   * @throws UnknownTransactionKeyCException
   */

  public String showTransaction(int index) throws UnknownTransactionKeyCException {
    return _warehouse.doShowTransaction(index);
  }

  /**
   * Registers a breakdown transaction
   *
   * @param partnerKey
   * @param productKey
   * @param amout
   * @throws UnknownPartnerKeyCException
   * @throws UnknownProductKeyCException
   * @throws UnavailableProductCException
   */

  public void registerBreakdownTransaction(String partnerKey, String productKey, int amout) throws UnknownPartnerKeyCException, UnknownProductKeyCException, UnavailableProductCException {
    _warehouse.doRegisterBreakdown(partnerKey, productKey, amout);
    dirtyBit = true;
  }

  /**
   * Registers a sale transaction
   *
   * @param partnerKey
   * @param productKey
   * @param amount
   * @param deadline
   * @throws UnknownPartnerKeyCException
   * @throws UnknownProductKeyCException
   * @throws UnavailableProductCException
   */

  public void registerSaleTransaction(String partnerKey, String productKey, int amount, int deadline) throws UnknownPartnerKeyCException, UnknownProductKeyCException, UnavailableProductCException {
    _warehouse.doRegisterSaleTransaction(partnerKey, productKey, amount, deadline);
    dirtyBit = true;
  }

  /**
   * Registers a acquisition transaction
   *
   * @param partnerKey
   * @param productKey
   * @param price
   * @param amount
   * @return boolean
   * @throws UnknownPartnerKeyCException
   * @throws UnknownProductKeyCException
   */

  public boolean registerAcquisitionTransaction(String partnerKey, String productKey, double price, int amount) throws UnknownPartnerKeyCException, UnknownProductKeyCException {
    boolean acquisitionCompleted = _warehouse.doRegisterAcquisitionTransaction(partnerKey, productKey, price, amount);
    if (acquisitionCompleted) {
      dirtyBit = true;
    }
    return acquisitionCompleted;

  }

  /**
   * Registers a new product in the context of an acquisition
   *
   * @param product
   * @param partnerKey
   * @param price
   * @param stock
   * @param reduction
   * @param recipe
   * @throws UnknownPartnerKeyCException
   * @throws UnknownProductKeyCException
   */

  public void registerNewProduct(String product, String partnerKey, double price, int stock, float reduction, String recipe) throws UnknownPartnerKeyCException, UnknownProductKeyCException {
    _warehouse.doRegisterBatch(product, partnerKey, price, stock, reduction, recipe);
    _warehouse.doRegisterTransactionNewProduct(product, partnerKey, price, stock, reduction, recipe);
    dirtyBit = true;
  }

  /**
   * Receive a payment of a sale
   *
   * @param transactionKey
   * @throws UnknownTransactionKeyCException
   */

  public void receivePayment(int transactionKey) throws UnknownTransactionKeyCException {
    _warehouse.doReceivePayment(transactionKey);
    dirtyBit = true;
  }

  /**
   * Returns a Collection of Batches with a price below the price given
   *
   * @param priceLimit
   * @return Collecion<Batch>
   */

  public Collection<Batch> lookupProductBatchesUnderGivenPrice(int priceLimit) {
    return _warehouse.doLookupProductBatchesUnderGivenPrice(priceLimit);
  }

  /**
   * Shows the all the payments made by the partner
   *
   * @param partnerKey
   * @return Collection<String>
   * @throws UnknownPartnerKeyCException
   */

  public Collection<String> lookupPaymentsByPartner(String partnerKey) throws UnknownPartnerKeyCException {
    return _warehouse.doLookupPaymentsByPartner(partnerKey);
  }

  /**
   * Returns the globalBalance
   *
   * @return double
   */

  public double showGlobalBalance() {
    return _warehouse.doShowGlobalBalance();
  }

  /**
   * Returns the balance of the warehouse including non paid sales
   *
   * @return double
   */

  public double showCurrentBalance() {
    return _warehouse.doShowCurrentBalance();
  }

}
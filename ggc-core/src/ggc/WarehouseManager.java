package ggc;

// export CVS_RSH=ssh
// export CVSROOT=:ext:ist199341@sigma.tecnico.ulisboa.pt:/afs/ist.utl.pt/groups/leic-po/po21/cvs/088
// export CLASSPATH=$(pwd)/po-uilib/po-uilib.jar:$(pwd)/ggc-core/ggc-core.jar:$(pwd)/ggc-app/ggc-app.jar
// java ggc.app.App

//FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.util.*;


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
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    //FIXME implement serialization method
    if (_filename == "") {
      throw new MissingFileAssociationException();
    }
    try {
      ObjectOutputStream out = new ObjectOutputStream((new FileOutputStream(_filename)));
      out.writeObject(_warehouse);
      out.close();

    } catch (FileNotFoundException e) {
      throw new FileNotFoundException();
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
    //FIXME implement serialization method
    try (ObjectInputStream in =
                 new ObjectInputStream((new FileInputStream(filename)))) {
      _warehouse = (Warehouse) in.readObject();
      _filename = filename;
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException /* FIXME maybe other exceptions */ | UnknownPartnerKeyCException | DuplicateClientCException e) {

      try {
        load(textfile);
      } catch (ClassNotFoundException f) {
        throw new ImportFileException(textfile);
      } catch (IOException f) {
        throw new ImportFileException(textfile);
      } catch (UnavailableFileException f) {
        throw new ImportFileException(textfile);
      }
    }

  }

  /**
   * Returns the time of the warehouse
   *
   * @@return time
   */
  public int time() {
    return _warehouse.doShowTime();
  }

  /**
   * Advances time, if time <= 0 throws Exception
   *
   * @@param timeToAdvance
   * @@throws InvalidDateException
   */
  public void AdvanceTime(int timeToAdvance) throws InvalidDateException {
    _warehouse.doAdvanceTime(timeToAdvance);
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
  }

  /**
   * Given an id, it returns the partner with that id
   *
   * @param id
   * @return Partner
   * @throws UnknownPartnerKeyCException
   */
  public Partner showPartner(String id) throws UnknownPartnerKeyCException {
    return _warehouse.doShowPartner(id);
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


}

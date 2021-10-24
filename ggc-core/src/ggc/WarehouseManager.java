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

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current store. */
  private String _filename = "";

  /** The warehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  private int time = 0;

  //FIXME define other attributes
  //FIXME define constructor(s)
  //FIXME define other methods

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    //FIXME implement serialization method
    if (_filename == "") {throw new MissingFileAssociationException();}
     ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
     out.writeObject(_warehouse);
     out.close();
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
  public void load(String filename) throws UnavailableFileException , IOException, ClassNotFoundException {
    //FIXME implement serialization method
    ObjectInputStream in =
            new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
    Warehouse _warehouse = (Warehouse) in.readObject();
    in.close();
  }
  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
	    _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException /* FIXME maybe other exceptions */| UnknownPartnerKeyException e) {

      throw new ImportFileException(textfile);
    }

  }


  public int time(){return this.time;}

  public void AdvanceTime(int timeToAdvance) throws  InvalidDateException {
  if (timeToAdvance < 0) throw new InvalidDateException(timeToAdvance);
  {time += timeToAdvance; }}

  public void registerPartner(String key,String name, String address){
        _warehouse.doRegisterPartner(key,name,address);
  }
  public Partner showPartner(String id) {return _warehouse.doShowPartner(id);}

  public Collection<Partner> ShowAllPartners(){return _warehouse.doShowAllPartners();}

public Collection<Product> showAllProducts(){
    return _warehouse.doShowAllProducts();
}

  public Collection<Product> showAllBatches(){
    return _warehouse.doShowAllBatches();
  }

}

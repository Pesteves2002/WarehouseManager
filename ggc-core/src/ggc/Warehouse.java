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
 */
public class Warehouse<newBatch> implements Serializable {

    /**
     * Serial number for serialization.
     */
    private static final long serialVersionUID = 202109192006L;

    // Insert by ID - Maybe use normal list
    private Map<Integer, Transaction> allTransactions = new TreeMap<Integer, Transaction>();

    // Insert by Name
    private Map<String, Partner> allPartners = new TreeMap<String, Partner>();

    private Map<String, Product> allProducts = new TreeMap<String, Product>();

    private Map<String, Batch> allBatches = new TreeMap<String, Batch>();


    // FIXME define attributes
    // FIXME define contructor(s)
    // FIXME define methods

    /**
     * @param txtfile filename to be loaded.
     * @throws IOException
     * @throws BadEntryException
     */
    void importFile(String txtfile) throws IOException, BadEntryException /* FIXME maybe other exceptions */, UnknownPartnerKeyCException {
        //FIXME implement method
        // super(txtfile);

        try (BufferedReader in = new BufferedReader(new FileReader(txtfile))) {
            String s;
            while ((s = in.readLine()) != null) {
                String line = new String(s.getBytes(), "UTF-8");

                String[] fields = line.split("\\|");
                switch (fields[0]) {
                    case "PARTNER" -> doRegisterPartner((fields[1]), (fields[2]), (fields[3]));
                    case "BATCH_S" -> doRegisterBatchS((fields[1]), fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]));
                    case "BATCH_M" -> doRegisterBatchM((fields[1]), fields[2], Integer.parseInt(fields[3]), Integer.parseInt(fields[4]), Double.parseDouble(fields[5]), fields[6]);
                    default -> throw new BadEntryException(fields[0]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } //catch (DuplicateClientException e) {
        // e.printStackTrace();}
        catch (BadEntryException e) {
            e.printStackTrace();
        } catch (UnknownPartnerKeyCException e) {
            throw new UnknownPartnerKeyCException(e.get_partnerName());
        }

    }


    void doRegisterPartner(String partnerKey, String partnerName, String partnerAddress) {
        allPartners.put(partnerKey, new Partner(partnerKey, partnerName, partnerAddress));
    }

    public Partner doShowPartner(String id) throws UnknownPartnerKeyCException {
        Partner partner = allPartners.get(id);
        if (partner == null) throw new UnknownPartnerKeyCException(id);
        return allPartners.get(id);
    }

    public Collection<Partner> doShowAllPartners() {
        return Collections.unmodifiableCollection(allPartners.values());
    }

    public void doRegisterBatchS(String product, String partnerKey, int price, int stock) throws UnknownPartnerKeyCException {

        if (allPartners.get(partnerKey) == null) {
            throw new UnknownPartnerKeyCException(partnerKey);
        }
        Product newProduct = new Product(product);
        allProducts.put(product, newProduct);


        Batch newBatch = new Batch(newProduct, price, stock, partnerKey);

        Partner p1 = allPartners.get(partnerKey);
        p1.addBatch(newBatch);
        allBatches.put(newBatch.getThisProductID(), newBatch);
    }


    public void doRegisterBatchM(String product, String partnerKey, int price, int stock, double reduction, String recipe) throws UnknownPartnerKeyCException {

        if (allPartners.get(partnerKey) == null) {
            throw new UnknownPartnerKeyCException(partnerKey);
        }
        Derived newProduct = new Derived(product, recipe);
        allProducts.put(product, newProduct);


        Batch newBatch = new Batch(newProduct, price, stock, partnerKey, reduction);

        Partner p1 = allPartners.get(partnerKey);
        p1.addBatch(newBatch);
        allBatches.put(newBatch.getThisProductID(), newBatch);
    }

    public Collection<Product> doShowAllProducts() {
        return Collections.unmodifiableCollection(allProducts.values());
    }

    public Collection<Batch> doShowAllBatches() {
        return Collections.unmodifiableCollection(allBatches.values());
    }

}


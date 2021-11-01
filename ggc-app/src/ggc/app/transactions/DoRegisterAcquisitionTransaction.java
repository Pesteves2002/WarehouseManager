package ggc.app.transactions;

import ggc.exceptions.UnknownKeyCException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.app.exceptions.UnknownProductKeyException;
import ggc.app.exceptions.UnavailableProductException;
// import ggc.exceptions.UnknownProductKeyCException; (TO DO)


/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
    addStringField("partnerKey", Prompt.partnerKey());
    addStringField("productKey", Prompt.productKey());
    addIntegerField("price", Prompt.price());
    addIntegerField("amount", Prompt.amount());
  }

  @Override
  public final void execute() throws CommandException {
    //FIXME implement command
    // FIXME implement exceptions
    try {


      String partnerKey = stringField("partnerKey");
      String productKey = stringField("productKey");
      int price = integerField("price");
      int amount = integerField("amount");
      if (! _receiver.registerAcquisitionTransaction(partnerKey, productKey, price, amount)){
        String hasRecipe = Form.requestString(Prompt.addRecipe());
        if (hasRecipe.equals("n")){
          _receiver.registerNewProduct(productKey, partnerKey, price, amount, 0, "");
        }
        else {
          int numberOfComponents = Form.requestInteger(Prompt.numberOfComponents());
          double reduction =  Form.requestReal(Prompt.alpha());

          String recipe = "";
          for (int i = 0; i < numberOfComponents; i++)
          {
            recipe += Form.requestString(Prompt.productKey());
            recipe += ":";
            recipe += Form.requestString(Prompt.amount());
            recipe += "#";
          }
          recipe = recipe.substring(0,recipe.length() - 1);
          _receiver.registerNewProduct(productKey, partnerKey, price, amount, (float) reduction, recipe);
          // mudar preco
        }
      }

    }
    catch (UnknownKeyCException e) {throw  new UnknownPartnerKeyException(e.getUnknownKey()); }
  }

}

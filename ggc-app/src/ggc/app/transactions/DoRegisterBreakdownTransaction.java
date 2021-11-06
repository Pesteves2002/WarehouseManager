package ggc.app.transactions;

import ggc.exceptions.UnavailableProductCException;
import ggc.exceptions.UnknownPartnerKeyCException;
import ggc.exceptions.UnknownProductKeyCException;
import pt.tecnico.uilib.forms.Field;
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
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("partnerKey", Prompt.partnerKey());
    addStringField("productKey", Prompt.productKey());
    addIntegerField("amount", Prompt.amount());
  }

  @Override
  public final void execute() throws CommandException {
    //FIXME implement command
    try {
      String partnerKey = stringField("partnerKey");
      String productKey = stringField("productKey");
      int amount = integerField("amount");
      _receiver.registerBreakdownTransaction(partnerKey, productKey, amount);
    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownPartnerKeyException(e.getUnknownKey());
    } catch (UnknownProductKeyCException e) {
      throw new UnknownProductKeyException(e.getUnknownKey());
    } catch (UnavailableProductCException e) {
      throw new UnavailableProductException(e.getProductKey(), e.getAmountRequested(), e.getAmountAvailable());
    }
  }
}

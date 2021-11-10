package ggc.app.partners;

import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.exceptions.UnknownPartnerKeyCException;
import ggc.exceptions.UnknownProductKeyCException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownProductKeyException;

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    addStringField("partnerKey", Prompt.partnerKey());
    addStringField("productKey", Prompt.productKey());
  }

  @Override
  public void execute() throws CommandException {
    try {
      String partnerKey = stringField("partnerKey");
      String productKey = stringField("productKey");
      _receiver.toggleProductNotifications(partnerKey, productKey);
    }
    catch (UnknownPartnerKeyCException e ) {throw  new UnknownPartnerKeyException(e.getUnknownKey());}
  catch (UnknownProductKeyCException e) {throw new UnknownProductKeyException(e.getUnknownKey());}
  }

}

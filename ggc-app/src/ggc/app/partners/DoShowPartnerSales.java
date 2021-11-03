package ggc.app.partners;

import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.exceptions.UnknownPartnerKeyCException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.DuplicatePartnerKeyException;
import ggc.exceptions.DuplicateClientCException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerSales extends Command<WarehouseManager> {

  DoShowPartnerSales(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_SALES, receiver);
    addStringField("partnerKey", Prompt.partnerKey());
  }

  @Override
  public void execute() throws CommandException {
    //FIXME implement command
    try {
      String partnerKey = stringField("partnerKey");
      _display.popup(_receiver.showPartnerSales(partnerKey));
    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownPartnerKeyException(e.getUnknownKey());
    }
  }

}

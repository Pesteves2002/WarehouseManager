package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.exceptions.UnknownPartnerKeyCException;
import ggc.app.exceptions.UnknownPartnerKeyException;

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("partnerKey", Prompt.partnerKey());

  }

  @Override
  public void execute() throws CommandException {
    try {
      String partnerKey = stringField("partnerKey");
      _display.popup(_receiver.showPartner(partnerKey));
    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownPartnerKeyException(e.getUnknownPartnerKey());
    }
  }
}



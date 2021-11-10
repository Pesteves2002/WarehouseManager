package ggc.app.partners;

import ggc.app.exceptions.UnknownPartnerKeyException;
import ggc.exceptions.UnknownPartnerKeyCException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    addStringField("partnerKey", Prompt.partnerKey());
  }

  @Override
  public void execute() throws CommandException {
    try {
      String partnerKey = stringField("partnerKey");
      _display.popup(_receiver.showPartnerAcquisitions(partnerKey));
    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownPartnerKeyException(e.getUnknownKey());
    }
  }
}

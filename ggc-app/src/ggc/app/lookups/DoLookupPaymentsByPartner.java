package ggc.app.lookups;

import ggc.exceptions.UnknownPartnerKeyCException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownPartnerKeyException;

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    addStringField("partnerKey", Prompt.partnerKey());
  }

  @Override
  public void execute() throws CommandException {
    try {
      String partnerKey = stringField("partnerKey");
      _display.popup(_receiver.lookupPaymentsByPartner(partnerKey));
    } catch (UnknownPartnerKeyCException e) {
      throw new UnknownPartnerKeyException(e.getUnknownKey());
    }
  }

}

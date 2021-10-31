package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.DuplicatePartnerKeyException;
import ggc.exceptions.DuplicateClientCException;

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    addStringField("partnerKey", Prompt.partnerKey());
    addStringField("partnerName", Prompt.partnerName());
    addStringField("partnerAddress", Prompt.partnerAddress());
  }

  @Override
  public void execute() throws CommandException {
    try {
      String partnerKey = stringField("partnerKey");
      String partnerName = stringField("partnerName");
      String partnerAddress = stringField("partnerAddress");
      _receiver.registerPartner(partnerKey, partnerName, partnerAddress);
    } catch (DuplicateClientCException e) {
      throw new DuplicatePartnerKeyException(e.getDuplicateKey());
    }
  }
}

package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
//FIXME import classes

/**
 * Register new partner.
 */
class DoRegisterPartner extends Command<WarehouseManager> {

  DoRegisterPartner(WarehouseManager receiver) {
    super(Label.REGISTER_PARTNER, receiver);
    //FIXME add command fields
    addStringField("key", Prompt.partnerKey());
    addStringField("name", Prompt.partnerName());
    addStringField("address", Prompt.partnerAddress());
  }

  @Override
  public void execute() throws CommandException {
    //FIXME implement command

      String key = stringField("key");
      String name = stringField("name");
      String address = stringField("address");
      _receiver.registerPartner(key,name,address);
  }

}

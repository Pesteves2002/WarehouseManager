package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;

//FIXME import classes

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    //FIXME add command fields
    addStringField("id", Prompt.partnerKey());

  }

  @Override
  public void execute() throws CommandException {
    //FIXME implement command
    String id = stringField("id");
    _display.popup(_receiver.showPartner(id));
  }

}
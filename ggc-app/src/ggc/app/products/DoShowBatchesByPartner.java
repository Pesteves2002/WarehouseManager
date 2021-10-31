package ggc.app.products;

import ggc.app.exceptions.UnknownPartnerKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.exceptions.UnknownKeyCException;

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> {

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    addStringField("partnerKey", Prompt.partnerKey());
  }

  @Override
  public final void execute() throws CommandException {
    //FIXME implement command
    try {
      String partnerKey = stringField("partnerKey");
      _display.popup(_receiver.showBatchesByPartner(partnerKey));
    }
    catch (UnknownKeyCException e)
    {throw new UnknownPartnerKeyException(e.getUnknownKey());
    }
  }

}

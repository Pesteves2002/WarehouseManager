package ggc.app.products;

import ggc.app.exceptions.UnknownProductKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.exceptions.UnknownKeyCException;

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    addStringField("productKey", Prompt.productKey());
  }

  @Override
  public final void execute() throws CommandException {
    // FIX ME
    try {
      String productKey = stringField("productKey");
      _display.popup( _receiver.showBatchesByProduct(productKey));
    } catch (UnknownKeyCException e) {
      throw new UnknownProductKeyException(e.getUnknownKey());
    }
  }

}

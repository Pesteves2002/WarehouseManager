package ggc.app.transactions;

import ggc.exceptions.UnknownTransactionKeyCException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownTransactionKeyException;


/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<WarehouseManager> {

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    addIntegerField("transactionKey", Prompt.transactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      int transactionKey = integerField("transactionKey");
      _display.popup(_receiver.showTransaction(transactionKey));
    }
    catch (UnknownTransactionKeyCException e) {throw  new UnknownTransactionKeyException(Integer.parseInt(e.getUnknownKey()));}
  }

}

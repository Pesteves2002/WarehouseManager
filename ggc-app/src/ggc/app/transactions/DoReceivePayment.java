package ggc.app.transactions;

import ggc.exceptions.UnknownTransactionKeyCException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.UnknownTransactionKeyException;

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    addIntegerField("transactionKey", Prompt.transactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      int transactionKey = integerField("transactionKey");
      _receiver.receivePayment(transactionKey);
    }
    catch (UnknownTransactionKeyCException e ) {throw new UnknownTransactionKeyException( Integer.parseInt(e.getUnknownKey()));}
  }

}

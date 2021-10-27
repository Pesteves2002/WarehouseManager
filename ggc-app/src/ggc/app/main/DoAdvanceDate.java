package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.InvalidDateException;

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    addIntegerField("timeToAdvance", Prompt.daysToAdvance());
  }

  @Override
  public final void execute() throws CommandException {
    try {
      int timeToAdvance = integerField("timeToAdvance");
      _receiver.AdvanceTime(timeToAdvance);
    } catch (ggc.exceptions.InvalidDateException e) {
      throw new InvalidDateException(e.getTimeToAdvance());
    }

  }
}

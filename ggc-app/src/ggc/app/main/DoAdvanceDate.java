package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.InvalidDateException;

//FIXME import classes

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

    DoAdvanceDate(WarehouseManager receiver) {
        super(Label.ADVANCE_DATE, receiver);
        addIntegerField("timeToAdvance", Prompt.daysToAdvance());
        //FIXME add command fields
    }

    @Override
    public final void execute() throws CommandException {
        //FIXME implement command
        try {
            int timeToAdvance = integerField("timeToAdvance");
            _receiver.AdvanceTime(timeToAdvance);
        } catch (ggc.exceptions.InvalidDateException e) {
            throw new InvalidDateException(e.getTimeToAdvance());
        }

    }
}

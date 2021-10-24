package ggc.app.main;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.exceptions.MissingFileAssociationException;
import java.io.IOException;
//FIXME import classes

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
    //FIXME maybe add command fields
  }

  @Override
  public final void execute() throws CommandException {
    //FIXME implement command
    try {_receiver.save();}
      catch (MissingFileAssociationException e) {
        try {
          String filename = Form.requestString(Prompt.newSaveAs());
        _receiver.saveAs(filename);}
          catch (IOException eS) {eS.printStackTrace();}
          catch (MissingFileAssociationException eS) {eS.printStackTrace();}
      }
    catch (IOException e) { e.printStackTrace();}
  }

}

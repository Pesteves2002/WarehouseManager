package ggc.app.main;

import ggc.exceptions.ImportFileException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.app.exceptions.FileOpenFailedException;

/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {

  /**
   * @param receiver
   */
  DoOpenFile(WarehouseManager receiver) {
    super(Label.OPEN, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    try {
      String filename = Form.requestString(Prompt.openFile());
      _receiver.importFile(filename);
    } catch (ImportFileException ufe) {
      throw new FileOpenFailedException(ufe.getMessage());
    }
  }
}

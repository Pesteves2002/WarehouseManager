package ggc.app.main;

import ggc.exceptions.ImportFileException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.WarehouseManager;
import ggc.exceptions.UnavailableFileException;
import ggc.app.exceptions.FileOpenFailedException;
//FIXME import classes

/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {

    /**
     * @param receiver
     */
    DoOpenFile(WarehouseManager receiver) {
        super(Label.OPEN, receiver);
        //FIXME maybe add command fields


    }

    @Override
    public final void execute() throws CommandException {

        try {
            String filename = Form.requestString(Prompt.openFile());


            //FIXME implement command
            _receiver.importFile(filename);
        }
    /*
     catch (UnavailableFileException ufe) {
      throw new FileOpenFailedException(ufe.getFilename());}
     */ catch (ImportFileException ufe) {
            ufe.printStackTrace();
        }

    }

}

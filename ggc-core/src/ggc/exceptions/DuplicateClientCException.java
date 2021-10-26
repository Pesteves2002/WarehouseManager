package ggc.exceptions;



public class DuplicateClientCException extends Exception {

    private static final long serialVersionUID = 202110262234L;

    private String _duplicateID;

    public DuplicateClientCException(String _duplicateID) {
        this._duplicateID = _duplicateID;
    }

    public String get_duplicateID() {
        return _duplicateID;
    }
}

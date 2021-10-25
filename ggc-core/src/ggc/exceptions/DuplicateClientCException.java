package ggc.exceptions;



public class DuplicateClientCException extends Exception {
    private String _duplicateID;

    public DuplicateClientCException(String _duplicateID) {
        this._duplicateID = _duplicateID;
    }

    public String get_duplicateID() {
        return _duplicateID;
    }
}

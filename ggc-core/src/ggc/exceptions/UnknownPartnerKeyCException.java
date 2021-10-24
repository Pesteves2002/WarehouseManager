package ggc.exceptions;

public class UnknownPartnerKeyCException extends Exception {

    private String _partnerName;

    public UnknownPartnerKeyCException(String partnerName) {
        _partnerName = partnerName;
    }

    public String get_partnerName() {
        return _partnerName;
    }
}

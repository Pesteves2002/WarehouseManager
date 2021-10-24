package ggc.exceptions;

public class UnknownPartnerKeyException extends Exception {

    private String _partnerName;

    public UnknownPartnerKeyException(String partnerName) {
        _partnerName = partnerName;
    }

    public String get_partnerName() {
        return _partnerName;
    }
}

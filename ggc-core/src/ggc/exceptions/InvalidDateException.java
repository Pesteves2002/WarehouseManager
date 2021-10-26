package ggc.exceptions;

public class InvalidDateException extends Exception{

    private static final long serialVersionUID = 202110262231L;

    private int timeToAdvance;

    public InvalidDateException(int timeToAdvance) {
        this.timeToAdvance = timeToAdvance;
    }

    public int getTimeToAdvance() {
        return timeToAdvance;
    }
}
package ggc.exceptions;

public class InvalidDateException extends Exception{

    private int timeToAdvance;

    public InvalidDateException(int timeToAdvance) {
        this.timeToAdvance = timeToAdvance;
    }

    public int getTimeToAdvance() {
        return timeToAdvance;
    }
}
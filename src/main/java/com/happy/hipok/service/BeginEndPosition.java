package com.happy.hipok.service;

/**
 * Utils to get a begin and end position.
 */
public class BeginEndPosition {

    private int beginPosition;

    private int endPosition;

    public BeginEndPosition(int beginPosition, int endPosition) {
        this.beginPosition = beginPosition;
        this.endPosition = endPosition;
    }

    public int getBeginPosition() {
        return beginPosition;
    }

    public void setBeginPosition(int beginPosition) {
        this.beginPosition = beginPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }
}

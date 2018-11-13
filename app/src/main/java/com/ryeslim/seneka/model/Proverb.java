package com.ryeslim.seneka.model;

public class Proverb {
    private short theID;
    private String text;

    public Proverb(short theID, String text) {
        this.theID = theID;
        this.text = text;
    }

    public short getTheID() {
        return theID;
    }

    public String getProverb() {
        return text;
    }
}

package com.jack_baretto.platiniumquiz;

/**
 * This class represent a choice for the QCM.
 * Created by Work on 13/12/2016.
 */
public class Choice {

    /** Label for the choice. */
    String label = "";

    public Choice(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}

package com.pik.application.dto;

import java.util.List;

public class PhraseList {

    private String phrase;
    private List<Long> chosenIds;

    public PhraseList(String phrase, List<Long> chosenIds) {
        this.phrase = phrase;
        this.chosenIds = chosenIds;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public List<Long> getChosenIds() {
        return chosenIds;
    }

    public void setChosenIds(List<Long> chosenIds) {
        this.chosenIds = chosenIds;
    }
}

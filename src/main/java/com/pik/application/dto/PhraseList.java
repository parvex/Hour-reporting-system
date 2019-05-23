package com.pik.application.dto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class PhraseList {

    private String phrase;
    private List<Long> chosenIds;
    private Pageable page;

    public PhraseList(String phrase, List<Long> chosenIds, Pageable page) {
        this.phrase = phrase;
        this.chosenIds = chosenIds;
        this.page = page;
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

    public Pageable getPage() {
        return page;
    }

    public void setPage(Pageable page) {
        this.page = page;
    }
}

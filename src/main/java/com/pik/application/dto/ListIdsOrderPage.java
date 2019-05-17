package com.pik.application.dto;

import java.util.List;

public class ListIdsOrderPage {

    private List<Long> criteria;
    private String order;
    private PageOptions options;


    public ListIdsOrderPage(List<Long> criteria, String order, PageOptions options) {
        this.criteria = criteria;
        this.order = order;
        this.options = options;
    }

    public List<Long> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<Long> criteria) {
        this.criteria = criteria;
    }

    public PageOptions getOptions() {
        return options;
    }

    public void setOptions(PageOptions options) {
        this.options = options;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

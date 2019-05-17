package com.pik.application.dto.WorkReportData;

import com.pik.application.dto.PageOptions;

public class IdStateOrderPage {

    private Long criteria;
    private Boolean state;
    private String order;
    private PageOptions options;

    public IdStateOrderPage(Long id, Boolean state, String order, PageOptions options) {
        this.criteria = id;
        this.state = state;
        this.order = order;
        this.options = options;
    }

    public Long getCriteria() {
        return criteria;
    }

    public void setCriteria(Long id) {
        this.criteria= id;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public PageOptions getOptions() {
        return options;
    }

    public void setOptions(PageOptions options) {
        this.options = options;
    }
}


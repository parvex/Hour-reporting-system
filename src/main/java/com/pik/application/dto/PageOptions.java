package com.pik.application.dto;

public class PageOptions {

    private Integer count;
    private Integer page;

    public PageOptions(Integer count, Integer page) {
        this.count = count;
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}

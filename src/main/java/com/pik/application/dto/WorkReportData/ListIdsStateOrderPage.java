package com.pik.application.dto.WorkReportData;

import com.pik.application.dto.ListIdsOrderPage;
import com.pik.application.dto.PageOptions;

import java.util.List;

public class ListIdsStateOrderPage extends ListIdsOrderPage {

    private Boolean state;

    public ListIdsStateOrderPage(List<Long> criteria, String order, PageOptions options, Boolean state) {
        super(criteria, order, options);
        this.state = state;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}

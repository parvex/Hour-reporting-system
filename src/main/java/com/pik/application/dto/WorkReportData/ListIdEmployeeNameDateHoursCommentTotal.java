package com.pik.application.dto.WorkReportData;

import java.util.List;

public class ListIdEmployeeNameDateHoursCommentTotal {

    private List<IdEmployeeNameDateHoursComment> list;
    private Integer totalCount;

    public ListIdEmployeeNameDateHoursCommentTotal(List<IdEmployeeNameDateHoursComment> list, Integer totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }

    public List<IdEmployeeNameDateHoursComment> getList() {
        return list;
    }

    public void setList(List<IdEmployeeNameDateHoursComment> list) {
        this.list = list;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
